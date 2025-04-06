package com.example.grade.model;

import java.io.Serializable;
import java.util.Objects;

public class GradeId implements Serializable {
    private String studentId;
    private String courseId;

    // Constructors
    public GradeId() {}

    public GradeId(String studentId, String courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    // Equals & hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GradeId)) return false;
        GradeId that = (GradeId) o;
        return Objects.equals(studentId, that.studentId) &&
                Objects.equals(courseId, that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseId);
    }
}
