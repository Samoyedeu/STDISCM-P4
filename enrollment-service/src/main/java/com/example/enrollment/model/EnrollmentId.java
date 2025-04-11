package com.example.enrollment.model;

import java.io.Serializable;
import java.util.Objects;

public class EnrollmentId implements Serializable {
    private String studentId;
    private String courseId;

    public EnrollmentId() {}

    public EnrollmentId(String studentId, String courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EnrollmentId)) return false;
        EnrollmentId that = (EnrollmentId) o;
        return Objects.equals(studentId, that.studentId) &&
                Objects.equals(courseId, that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseId);
    }
}
