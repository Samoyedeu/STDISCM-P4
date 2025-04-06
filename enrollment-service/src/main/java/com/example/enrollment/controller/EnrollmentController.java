package com.example.enrollment.controller;

import com.example.enrollment.model.Enrollment;
import com.example.enrollment.repository.EnrollmentRepository;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;  // For RestTemplate
import java.util.Map;  // For Map
import org.springframework.beans.factory.annotation.Value;  // For @Value annotation
import org.springframework.web.bind.annotation.RequestParam;  // For @RequestParam
import org.springframework.web.bind.annotation.RequestBody;  // For @RequestBody
import org.springframework.web.bind.annotation.PostMapping;  // For @PostMapping
import org.springframework.web.bind.annotation.GetMapping;  // For @GetMapping

import java.util.List;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Value("${course-service.url}")
    private String courseServiceUrl;

    private final RestTemplate restTemplate;

    public EnrollmentController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Endpoint to seed sample data
    @PostMapping("/seed")
    public String seedData() {
        if (enrollmentRepository.count() == 0) {
            enrollmentRepository.save(new Enrollment("student1", "CSE101"));
            enrollmentRepository.save(new Enrollment("student1", "MTH202"));
            return "Sample enrollments added!";
        }
        return "Enrollments already exist.";
    }

    // Get enrollments by student
    @GetMapping
    public List<Enrollment> getEnrollments(@RequestParam String studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    // Fetch courses for enrollment
    @GetMapping("/courses")
    public List<Map<String, Object>> getCourses() {
        String url = courseServiceUrl + "/courses";
        List<Map<String, Object>> courses = restTemplate.getForObject(url, List.class);
        return courses;
    }

    // Enrollment process (add new enrollment)
    @PostMapping
    public String addEnrollment(@RequestBody Enrollment enrollment) {
        String studentId = enrollment.getStudentId();
        String courseId = enrollment.getCourseId();
        
        Enrollment newEnrollment = new Enrollment(studentId, courseId);
        enrollmentRepository.save(newEnrollment);
        return "Enrollment added successfully for " + studentId + " in course " + courseId;
    }

}
