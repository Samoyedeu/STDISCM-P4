document.getElementById('loginForm').addEventListener('submit', function (e) {
    e.preventDefault();
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    fetch('http://localhost:8081/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username, password })
    })
    .then(res => res.json())
    .then(data => {
        localStorage.setItem('jwtToken', data.token);
        alert('Login successful!');
        window.location.href = 'courses.html';
    })
    .catch(err => {
        console.error('Login failed:', err);
        alert('Login failed');
    });
});