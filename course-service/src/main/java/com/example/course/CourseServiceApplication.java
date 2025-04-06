
package com.example.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.example.course.repository.CourseRepository;
import com.example.course.model.Course;

@SpringBootApplication
public class CourseServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CourseServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner run(CourseRepository courseRepository) {
        return args -> {
            if (courseRepository.count() == 0) {
                courseRepository.save(new Course("CSE101", "Intro to Computer Science", "Prof. Reyes"));
                courseRepository.save(new Course("MTH202", "Advanced Math", "Prof. Dela Cruz"));
                System.out.println("Seeded initial courses.");
            }
        };
    }
}
