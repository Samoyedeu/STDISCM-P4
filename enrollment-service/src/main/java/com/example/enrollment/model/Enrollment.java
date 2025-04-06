package com.example.enrollment.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "enrollments")
@IdClass(EnrollmentId.class)
public class Enrollment {
    @Id
    private String studentId;

    @Id
    private String courseId;

    public Enrollment() {}

    public Enrollment(String studentId, String courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    // Getters and setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
