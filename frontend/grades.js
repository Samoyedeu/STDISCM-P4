document.addEventListener('DOMContentLoaded', function () {
    const token = localStorage.getItem('jwtToken');

    if (!token) {
        alert('Not logged in! Redirecting to login.');
        window.location.href = 'login.html';
        return;
    }

    document.getElementById('loadGradesBtn').addEventListener('click', function () {
        console.log("TOKEN: ", token, "")
        fetch('http://localhost:8083/grades', {
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
                const list = document.getElementById('gradeList');
                list.innerHTML = '';
                data.forEach(grade => {
                    const li = document.createElement('li');
                    li.textContent = `${grade.courseId}: ${grade.grade}`;
                    list.appendChild(li);
                });
            })
            .catch(err => {
                console.error('Failed to load grades:', err);
                alert('Unauthorized or error loading grades.');
            });
    });

    document.getElementById('logoutBtn').addEventListener('click', function () {
        localStorage.removeItem('jwtToken');
        window.location.href = 'login.html';
    });
});
