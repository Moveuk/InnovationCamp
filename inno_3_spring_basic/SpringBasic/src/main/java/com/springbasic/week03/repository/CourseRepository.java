package com.springbasic.week03.repository;

import com.springbasic.week03.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
