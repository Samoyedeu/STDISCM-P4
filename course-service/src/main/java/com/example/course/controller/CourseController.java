
package com.example.course.controller;

import com.example.course.model.Course;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @GetMapping
    public ResponseEntity<List<Course>> getCourses() {
        System.out.println("GET /courses called");
        List<Course> courses = List.of(
            new Course("CSE101", "Intro to Computer Science", "Prof. Reyes"),
            new Course("MTH202", "Advanced Math", "Prof. Dela Cruz")
        );
        return ResponseEntity.ok(courses);
    }
}
