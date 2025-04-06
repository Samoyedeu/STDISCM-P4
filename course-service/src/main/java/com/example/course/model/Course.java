
package com.example.course.model;

public class Course {
    private String id;
    private String name;
    private String instructor;

    public Course(String id, String name, String instructor) {
        this.id = id;
        this.name = name;
        this.instructor = instructor;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getInstructor() { return instructor; }
}
