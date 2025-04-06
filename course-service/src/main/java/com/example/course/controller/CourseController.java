
package com.example.course.controller;

import com.example.course.model.Course;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.course.repository.CourseRepository;


import java.util.List;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "*")
public class CourseController {

    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
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
}