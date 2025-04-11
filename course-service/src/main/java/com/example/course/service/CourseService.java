package com.example.course.service;

import com.example.course.model.Course;
import com.example.course.repository.CourseRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAvailableCourses() {
        return courseRepository.findAll();
    }
}
