package com.example.grade.model;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
@Entity
@Table(name = "grades")
@IdClass(GradeId.class)
public class Grade {
    @Id
    private String studentId;

    @Id
    private String courseId;
    private String grade;

    public Grade(String studentId, String courseId, String grade) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.grade = grade;
    }
    // ✅ No-arg constructor required by JPA
    public Grade() {}

    public String getStudentId() { return studentId; }
    public String getCourseId() { return courseId; }
    public String getGrade() { return grade; }

    public void setStudentId(String studentId) { this.studentId = studentId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
    public void setGrade(String grade) { this.grade = grade; }
}
