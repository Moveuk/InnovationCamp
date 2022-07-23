package com.springbasic.week02.repository;

import com.springbasic.week02.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
