package com.example.course.controller;

import com.example.course.model.Course;
import com.example.course.repository.CourseRepository;
import com.example.course.service.CourseService;  // Add the service class import
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class CourseController {

    private final CourseRepository courseRepository;
    private final CourseService courseService;  // Declare courseService

    // Constructor to inject both the repository and service
    public CourseController(CourseRepository courseRepository, CourseService courseService) {
        this.courseRepository = courseRepository;
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Optional: Seed sample data if DB is empty
    @PostMapping("/seed")
    public String seed() {
        if (courseRepository.count() == 0) {
            courseRepository.save(new Course("CSE101", "Intro to Computer Science", "Prof. Reyes"));
            courseRepository.save(new Course("MTH202", "Advanced Math", "Prof. Dela Cruz"));
            return "Sample courses added!";
        }
        return "Courses already exist.";
    }

    // Get available courses for enrollment as JSON
    @GetMapping("/enroll")
    public ResponseEntity<List<Course>> enroll() {
        List<Course> availableCourses = courseService.getAvailableCourses();
        return ResponseEntity.ok(availableCourses);
    }
}
