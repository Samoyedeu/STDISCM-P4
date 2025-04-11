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
    const role = decoded?.role;

    const gradeList = document.getElementById('gradeList');
    const facultyFormContainer = document.getElementById('facultyFormContainer');
    const uploadForm = document.getElementById('uploadGradeForm');

    function renderGrades(grades, isFaculty) {
        gradeList.innerHTML = '';
        if (isFaculty) {
            const heading = document.createElement('h3');
            heading.textContent = "All Student Grades";
            gradeList.appendChild(heading);
        }
        grades.forEach(grade => {
            const li = document.createElement('li');
            li.textContent = isFaculty
                ? `${grade.studentId} - ${grade.courseId}: ${grade.grade}`
                : `${grade.courseId}: ${grade.grade}`;
            gradeList.appendChild(li);
        });
    }

    if (role === 'student') {
        fetch('http://localhost:8083/grades', {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        })
            .then(res => res.json())
            .then(data => {
                renderGrades(data, false);
            })
            .catch(err => {
                console.error('Failed to load student grades:', err);
                alert('Error loading grades.');
            });

    } else if (role === 'faculty') {
        // Show the form
        facultyFormContainer.style.display = 'block';

        fetch('http://localhost:8083/grades/all', {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        })
            .then(res => res.json())
            .then(data => {
                renderGrades(data, true);
            })
            .catch(err => {
                console.error('Failed to load all grades:', err);
                alert('Error loading all student grades.');
            });

        // Handle form submission
        uploadForm.addEventListener('submit', function (e) {
            e.preventDefault();

            const studentId = document.getElementById('studentId').value;
            const courseId = document.getElementById('courseId').value;
            const grade = document.getElementById('grade').value;

            fetch('http://localhost:8083/grades', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + token
                },
                body: JSON.stringify({ studentId, courseId, grade })
            })
                .then(res => {
                    if (!res.ok) throw new Error('Failed to submit grade');
                    return res.text();
                })
                .then(msg => {
                    alert("Grade submitted successfully!");
                    // Optionally refresh the list
                    return fetch('http://localhost:8083/grades/all', {
                        headers: { 'Authorization': 'Bearer ' + token }
                    });
                })
                .then(res => res.json())
                .then(updatedGrades => {
                    renderGrades(updatedGrades, true);
                    uploadForm.reset(); // Clear the form
                })
                .catch(err => {
                    console.error("Error submitting grade:", err);
                    alert("Failed to submit grade.");
                });
        });
    }

    document.getElementById('logoutBtn').addEventListener('click', function () {
        localStorage.removeItem('jwtToken');
        window.location.href = 'login.html';
    });

    document.getElementById('viewCoursesBtn').addEventListener('click', function () {
        window.location.href = 'courses.html';
    });
});
