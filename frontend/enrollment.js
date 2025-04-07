let token = localStorage.getItem('jwtToken');

    if (!token) {
      alert('Not logged in! Redirecting to login.');
      window.location.href = 'login.html';
    }

    const studentId = JSON.parse(atob(token.split('.')[1])).sub; // Extract student ID from JWT

    // Load all courses and show available ones only
    fetch("http://localhost:8084/enrollments/courses", {
    headers: { "Authorization": `Bearer ${token}` }
    })
    .then(res => res.json())
    .then(courses => {
    if (!Array.isArray(courses)) throw new Error("Courses response is not a list");

    fetch(`http://localhost:8084/enrollments?studentId=${studentId}`, {
        headers: { "Authorization": `Bearer ${token}` }
    })
    .then(res => res.json())
    .then(enrollments => {
        const enrolledCourseIds = new Set(enrollments.map(e => e.courseId));
        const courseList = document.getElementById("courseList");
        const enrolledList = document.getElementById("enrolledCoursesList");

        courseList.innerHTML = "";
        enrolledList.innerHTML = "";

        courses.forEach(course => {
        const li = document.createElement("li");
        li.textContent = `${course.name} - ${course.instructor}`;

        if (enrolledCourseIds.has(course.id)) {
            enrolledList.appendChild(li); // Display in enrolled section
        } else {
            const enrollButton = document.createElement("button");
            enrollButton.textContent = "Enroll Now";
            enrollButton.onclick = () => enrollInCourse(course.id);
            li.appendChild(enrollButton);
            courseList.appendChild(li); // Display in available section
        }
        });
    });
    })
    .catch(err => {
    console.error("Error fetching courses:", err);
    alert("Failed to load courses");
    });
    

    // Enroll function
    function enrollInCourse(courseId) {
      fetch("http://localhost:8084/enrollments", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          studentId: studentId,
          courseId: courseId
        })
      })
      .then(res => res.text())
      .then(response => {
        alert("Enrolled successfully!");
        console.log(response);
        loadCourses(); // Refresh both lists
      })
      .catch(err => {
        console.error("Enrollment failed:", err);
        alert("Error enrolling in course");
      });
    }