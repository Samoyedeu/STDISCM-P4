document.addEventListener('DOMContentLoaded', function () {
    const token = localStorage.getItem('jwtToken');

    function parseJwt(token) {
        try {
            const base64Payload = token.split('.')[1];
            const payload = atob(base64Payload);
            return JSON.parse(payload);
        } catch (e) {
            return null;
        }
    }


    if (!token) {
        alert('Not logged in! Redirecting to login.');
        window.location.href = 'login.html';
        return;
    }

    const decoded = parseJwt(token);
    if (!decoded || decoded.role !== 'student') {
        alert('Access denied: Only students can view grades.');
        window.location.href = 'courses.html'; // or a safe page
    }

    document.getElementById('loadGradesBtn').addEventListener('click', function () {
        fetch('http://localhost:8083/grades', {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + token
            }
        })
            .then(res => {
                console.log("Response status:", res.status); // 👈 Add this
                if (!res.ok) throw new Error('Unauthorized');
                return res.json();
            })
            .then(data => {
                console.log("Grades:", data); // 👈 Add this
                const list = document.getElementById('gradeList');
                list.innerHTML = '';
                data.forEach(grade => {
                    const li = document.createElement('li');
                    li.textContent = `${grade.courseId}: ${grade.grade}`;
                    list.appendChild(li);
                });
            })
            .catch(err => {
                console.error('Failed to load grades:', err); // 👈 Already exists
                alert('Error loading grades.');
            });

    });

    document.getElementById('logoutBtn').addEventListener('click', function () {
        localStorage.removeItem('jwtToken');
        window.location.href = 'login.html';
    });

    document.getElementById('viewCoursesBtn').addEventListener('click', function () {
        window.location.href = 'courses.html';
    })
});

