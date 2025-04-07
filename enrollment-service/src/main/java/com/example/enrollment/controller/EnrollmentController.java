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

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;


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

    @GetMapping("/courses")
    public ResponseEntity<?> getCourses(@RequestHeader("Authorization") String authHeader) {
        String url = courseServiceUrl + "/courses";
        System.out.println("Fetching courses from: " + url);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);  // Pass the JWT token
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<List> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                List.class
            );

            List<Map<String, Object>> courses = response.getBody();
            System.out.println("Courses fetched: " + courses);
            return ResponseEntity.ok(courses);

        } catch (HttpClientErrorException e) {
            System.err.println("Error fetching courses: " + e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }




    @PostMapping
    public String addEnrollment(@RequestBody Enrollment enrollment) {
        System.out.println("Received enrollment request: studentId = " + enrollment.getStudentId() + ", courseId = " + enrollment.getCourseId());
        
        Enrollment newEnrollment = new Enrollment(enrollment.getStudentId(), enrollment.getCourseId());
        enrollmentRepository.save(newEnrollment);
        return "Enrollment added successfully";
    }


}
