document.getElementById('customerForm').addEventListener('submit', async function(event) {
    event.preventDefault();

    // Retrieve form data
    const customerData = {
        first_name: document.getElementById('firstName').value,
        last_name: document.getElementById('lastName').value,
        street: document.getElementById('street').value,
        address: document.getElementById('address').value,
        city: document.getElementById('city').value,
        state: document.getElementById('state').value,
        email: document.getElementById('email').value,
        phone: document.getElementById('phone').value,
    };

    const messageElement = document.getElementById('message');

    try {
        // Retrieve the JWT token from localStorage
        const token = localStorage.getItem('jwt');

        const response = await fetch('https://hiteshsunbase-production.up.railway.app/customers', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}` // Include Bearer token for authentication
            },
            body: JSON.stringify(customerData)
        });

        if (response.ok) {
            messageElement.textContent = `Customer created successfully!`;
            messageElement.style.color = 'green';

            // Clear form fields after successful creation
            document.getElementById('customerForm').reset();
        } else {
            messageElement.textContent = 'Failed to create customer. Please try again.';
            messageElement.style.color = 'red';
        }
    } catch (error) {
        console.error('Error:', error);
        messageElement.textContent = 'An error occurred. Please try again later.';
        messageElement.style.color = 'red';
    }
});
