const searchForm = document.getElementById("searchForm");

searchForm.addEventListener("submit", (event) => {
  event.preventDefault(); // Prevent the default form submission

  const columnSelect = document.getElementById("columnSelect");
  const searchInput = document.getElementById("searchInput");

  const column = columnSelect.value;
  const search = searchInput.value.trim();

  if (search) {
    fetchCustomers(column, search);
  } else {
    fetchCustomers();
  }
});

async function fetchCustomers(column = "", search = "") {
  try {
    const url = new URL(
      "https://hiteshsunbase-production.up.railway.app/customers",
      window.location.origin
    );

    if (column) {
      url.searchParams.append("column", column);
    }
    if (search) {
      url.searchParams.append("search", search);
    }

    const token = localStorage.getItem("jwt");

    const response = await fetch(url, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    });

    if (response.ok) {
      const customerPage = await response.json();

      // Example: Update the DOM to display the customers
      loadCustomers(customerPage.content);

      // Handle pagination details if needed
    } else {
      console.error(
        "Failed to fetch customers:",
        response.status,
        response.statusText
      );
    }
  } catch (error) {
    console.error("Error fetching customers:", error);
  }
}

// Function to load customers into the table
function loadCustomers(customers) {
  const tableBody = document.querySelector("#customerTable tbody");
  tableBody.innerHTML = ""; // Clear any existing rows

  customers.forEach((customer) => {
    const row = document.createElement("tr");

    // Create a cell for each piece of customer data
    Object.keys(customer).forEach((key) => {
      const cell = document.createElement("td");
      const input = document.createElement("input");
      input.type = "text";
      input.value = customer[key];
      input.name = key;
      input.classList.add("customer-input");
      input.disabled = true; // Disable input initially
      cell.appendChild(input);
      row.appendChild(cell);
    });

    // Create actions cell
    const actionsCell = document.createElement("td");

    const editButton = document.createElement("button");
    editButton.textContent = "Edit";
    editButton.classList.add("edit-btn");
    editButton.addEventListener("click", () => {
      editCustomer(row);
    });
    actionsCell.appendChild(editButton);

    // Save button
    const saveButton = document.createElement("button");
    saveButton.textContent = "Save";
    saveButton.classList.add("edit-btn");
    saveButton.style.display = "none"; // Initially hidden
    saveButton.addEventListener("click", () => {
      saveCustomer(row);
    });
    actionsCell.appendChild(saveButton);

    // Delete button
    const deleteButton = document.createElement("button");
    deleteButton.textContent = "Delete";
    deleteButton.classList.add("delete-btn");
    deleteButton.addEventListener("click", () =>
      deleteCustomer(customer["uuid"])
    );
    actionsCell.appendChild(deleteButton);

    row.appendChild(actionsCell);

    tableBody.appendChild(row);
  });
}

function editCustomer(row) {
  const inputs = row.querySelectorAll("input");
  inputs.forEach((input) => {
    input.disabled = false; // Enable inputs
  });

  row.querySelector(".edit-btn").style.display = "none"; // Hide Edit button
  row.querySelector(".edit-btn + button").style.display = "inline"; // Show Save button
}
function saveCustomer(row) {
  const inputs = row.querySelectorAll("input");
  const updatedCustomer = {};

  inputs.forEach((input) => {
    updatedCustomer[input.name] = input.value; // Collect updated values
  });
  console.log("updatedCustomer", updatedCustomer);
  const token = localStorage.getItem("jwt");
  // Assuming you have an API endpoint to update customer data
  fetch(`https://hiteshsunbase-production.up.railway.app/customers`, {
    method: "PUT",
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(updatedCustomer),
  })
    .then((response) => response.json())
    .then((data) => {
      console.log("Customer updated:", data);
      // Reload customers or update the table to reflect changes
      fetchCustomers(); // Assuming `data.customers` is the updated list of customers
    })
    .catch((error) => console.error("Error updating customer:", error));

  // row.querySelector(".edit-btn").style.display = "inline"; // Show Edit button
  // row.querySelector(".edit-btn + button").style.display = "none"; // Hide Save button
}

// Function to handle deleting a customer
async function deleteCustomer(id) {
  if (confirm(`Are you sure you want to delete the customer with ID: ${id}?`)) {
    try {
      // Retrieve the JWT token from localStorage
      const token = localStorage.getItem("jwt");

      const response = await fetch(`https://hiteshsunbase-production.up.railway.app/customers/${id}`, {
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });

      if (response.ok) {
        const message = await response.text();
        alert(`Success: ${message}`);

        // Optionally, refresh the customer list or update the UI
        fetchCustomers();
      } else {
        alert("Failed to delete customer. Please try again.");
      }
    } catch (error) {
      console.error("Error:", error);
      alert("An error occurred while trying to delete the customer.");
    }
  }
}

async function sync() {
    try {
      // Retrieve the JWT token from localStorage
      const token = localStorage.getItem("jwt");
      
      const access_token = localStorage.getItem("access_token");

      const response = await fetch(`https://hiteshsunbase-production.up.railway.app/customers/sync`, {
        method: "PATCH",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ access_token: access_token })
      });

      if (response.ok) {
        const message = await response.text();
        alert(`Success: ${message}`);

        fetchCustomers();
      } else {
        alert("Failed to sync customer. Please try again.");
      }
    } catch (error) {
      console.error("Error:", error);
      alert("An error occurred while trying to sync the customer.");
    }
  }

document
  .getElementById("createCustomerBtn")
  .addEventListener("click", function () {
    window.location.href = "../html/customer.html";
  });
  
document
.getElementById("sync")
.addEventListener("click", function () {
  sync();
});

// Load customers when the page is loaded
document.addEventListener("DOMContentLoaded", fetchCustomers);
