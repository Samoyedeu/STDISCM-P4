let token = localStorage.getItem('jwtToken');
const studentId = 'student1';  // This would normally be retrieved from the logged-in user's token

if (!token) {
    alert('Not logged in! Redirecting to login.');
    window.location.href = 'login.html';
}

document.getElementById('loadEnrollmentsBtn').addEventListener('click', function () {
    fetch(`http://localhost:8083/enrollments?studentId=${studentId}`, {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        }
    })
        .then(res => res.json())
        .then(data => {
            const list = document.getElementById('enrollmentList');
            list.innerHTML = '';
            data.forEach(enrollment => {
                const li = document.createElement('li');
                li.textContent = `Course: ${enrollment.courseId}`;
                list.appendChild(li);
            });
        })
        .catch(err => {
            console.error('Failed to load enrollments:', err);
            alert('Unauthorized or error loading enrollments.');
        });
});

document.getElementById('enrollBtn').addEventListener('click', function () {
    const courseId = document.getElementById('courseId').value;
    
    if (!courseId) {
        alert('Please select a course.');
        return;
    }

    const enrollmentData = {
        studentId: studentId,
        courseId: courseId
    };

    fetch('http://localhost:8083/enrollments', {
        method: 'POST',
        headers: {
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(enrollmentData)
    })
        .then(res => res.json())
        .then(data => {
            alert('Enrollment successful!');
            loadEnrollments();  // Refresh the list
        })
        .catch(err => {
            console.error('Error enrolling:', err);
            alert('Failed to enroll.');
        });
});

// Load the current enrollments on page load
function loadEnrollments() {
    fetch(`http://localhost:8083/enrollments?studentId=${studentId}`, {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        }
    })
        .then(res => res.json())
        .then(data => {
            const list = document.getElementById('enrollmentList');
            list.innerHTML = '';
            data.forEach(enrollment => {
                const li = document.createElement('li');
                li.textContent = `Course: ${enrollment.courseId}`;
                list.appendChild(li);
            });
        })
        .catch(err => {
            console.error('Failed to load enrollments:', err);
            alert('Unauthorized or error loading enrollments.');
        });
}

loadEnrollments();  // Call to load the current enrollments immediately after page load
