package com.example.enrollment.repository;

import com.example.enrollment.model.Enrollment;
import com.example.enrollment.model.EnrollmentId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentId> {
    List<Enrollment> findByStudentId(String studentId);
}
