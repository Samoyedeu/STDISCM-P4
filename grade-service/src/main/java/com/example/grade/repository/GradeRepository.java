package com.example.grade.repository;

import com.example.grade.model.Grade;
import com.example.grade.model.GradeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, GradeId> {
    List<Grade> findByStudentId(String studentId);
}
