const token = localStorage.getItem('jwtToken');

if (!token) {
    alert('Not logged in! Redirecting to login.');
    window.location.href = 'login.html';
}

document.getElementById('loadCoursesBtn').addEventListener('click', function () {
    const token = localStorage.getItem('jwtToken'); // ✅ same key used as in login.js

    console.log("TOKEN:", token); // good for debugging

    fetch('http://localhost:8082/courses', {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        }
    })
        .then(res => {
            if (!res.ok) throw new Error('Unauthorized');
            return res.json();
        })
        .then(data => {
            const list = document.getElementById('courseList');
            list.innerHTML = '';
            data.forEach(course => {
                const li = document.createElement('li');
                li.textContent = `${course.id} - ${course.name} (${course.instructor})`;
                list.appendChild(li);
            });
        })
        .catch(err => {
            console.error('Failed to load courses:', err);
            alert('Unauthorized or error loading courses.');
        });
});

document.getElementById('logoutBtn').addEventListener('click', function () {
    localStorage.removeItem('token');
    window.location.href = 'login.html';
});

document.getElementById('viewGradesBtn').addEventListener('click', function () {
    window.location.href = 'grades.html';
});
