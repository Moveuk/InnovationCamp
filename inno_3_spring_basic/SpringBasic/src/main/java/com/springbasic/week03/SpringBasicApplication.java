package com.springbasic.week03;

import com.springbasic.week03.domain.Course;
import com.springbasic.week03.repository.CourseRepository;
import com.springbasic.week03.service.CourseService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@EnableJpaAuditing
@SpringBootApplication
public class SpringBasicApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBasicApplication.class, args);

        // 여기에 작성!
    }


    @Bean
    public CommandLineRunner demo(CourseRepository courseRepository, CourseService courseService) {
        return (args) -> {
            // 데이터 저장하기
            courseRepository.save(new Course("프론트엔드의 꽃, 리액트", "임민영"));

            // 데이터 전부 조회하기
            List<Course> courseList = courseRepository.findAll();
            for (int i = 0; i < courseList.size(); i++) {
                Course course = courseList.get(i);
                System.out.println(course.getId());
                System.out.println(course.getTitle());
                System.out.println(course.getTutor());
            }

            // 데이터 하나 조회하기
            Course course = courseRepository.findById(1L).orElseThrow(
                    () -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다.")
            );

            Course new_course = new Course("웹개발의 봄, Spring", "임민영");
            courseService.update(1L, new_course);
            courseList = courseRepository.findAll();
            for (int i=0; i<courseList.size(); i++) {
                course = courseList.get(i);
                System.out.println(course.getId());
                System.out.println(course.getTitle());
                System.out.println(course.getTutor());
            }

            courseRepository.deleteAll();

        };
    }
}
