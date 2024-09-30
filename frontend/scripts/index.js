document.getElementById('loginForm').addEventListener('submit', async function(event) {
    event.preventDefault();

    const email = document.getElementById('loginId').value;
    const password = document.getElementById('password').value;
    const messageElement = document.getElementById('message');
    
    try {
        const response = await fetch('https://hiteshsunbase-production.up.railway.app/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ email, password })
        });

        if (response.ok) {
            data = await response.json();
            console.log(data);
            const jwt =  data.jwt;
            const access_token = data.access_token;

            messageElement.textContent = 'Login successful!';
            messageElement.style.color = 'green';

            // Store the JWT token (for example, in localStorage)
            localStorage.setItem('jwt', jwt);           
            localStorage.setItem('access_token', access_token);
            // Redirect to the customer creation page or another protected page
            setTimeout(() => {
                window.location.href = '../html/table.html';
            }, 1000); // Redirect after 1 second

        } else {
            messageElement.textContent = 'Invalid email or password. Please try again.';
            messageElement.style.color = 'red';
        }
    } catch (error) {
        console.error('Error:', error);
        messageElement.textContent = 'An error occurred. Please try again later.';
        messageElement.style.color = 'red';
    }
    
   
});

