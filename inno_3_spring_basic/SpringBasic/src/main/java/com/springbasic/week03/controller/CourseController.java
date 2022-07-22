package com.springbasic.week03.controller;

import com.springbasic.week03.model.Course;
import com.springbasic.week03.model.Tutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {

    @GetMapping("/courses")
    public Course getCourses() {
        Course course = new Course();
        course.setTitle("웹개발의 봄 스프링");
        course.setDays(35);
        course.setTutor(new Tutor("남병관","신입"));
        return course;
    }
}