package com.example.grade.controller;

import com.example.grade.model.Grade;
import com.example.grade.repository.GradeRepository;
import com.example.grade.model.GradeId;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grades")
public class GradeController {

    @Autowired
    private GradeRepository gradeRepository;

    // ✅ Student-only endpoint to get their own grades
    @GetMapping
    public ResponseEntity<?> getGrades(@RequestAttribute("claims") Claims claims) {
        String role = (String) claims.get("role");
        if (!"student".equals(role)) {
            return ResponseEntity.status(403).body("Access denied: only students can view grades.");
        }

        String studentId = claims.getSubject();
        List<Grade> grades = gradeRepository.findByStudentId(studentId);
        return ResponseEntity.ok(grades);
    }
    // ✅ Optional: Seeding grades (for testing only, can be secured further)
    @PostMapping("/seed")
    public String seedGrades() {
        if (gradeRepository.count() == 0) {
            gradeRepository.save(new Grade("student1", "CSE101", "A"));
            gradeRepository.save(new Grade("student1", "MTH202", "B+"));
            return "Sample grades added!";
        }
        return "Grades already exist.";
    }

    @PostMapping
    public ResponseEntity<?> uploadGrade(@RequestBody Grade grade, @RequestHeader("Authorization") String authHeader) {
        if (grade.getStudentId() == null || grade.getCourseId() == null || grade.getGrade() == null) {
            return ResponseEntity.badRequest().body("Missing required fields");
        }

        // Save or update the grade using the composite ID
        GradeId id = new GradeId(grade.getStudentId(), grade.getCourseId());
        if (gradeRepository.existsById(id)) {
            // Update existing grade
            Grade existing = gradeRepository.findById(id).get();
            existing.setGrade(grade.getGrade());
            gradeRepository.save(existing);
            return ResponseEntity.ok("Grade updated.");
        }

        // Insert new grade
        gradeRepository.save(grade);
        return ResponseEntity.ok("Grade uploaded.");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllGrades(@RequestHeader("Authorization") String authHeader) {
        // Optional: check for faculty role using JWT claims here if needed
        List<Grade> grades = gradeRepository.findAll();
        return ResponseEntity.ok(grades);
    }
    
}
