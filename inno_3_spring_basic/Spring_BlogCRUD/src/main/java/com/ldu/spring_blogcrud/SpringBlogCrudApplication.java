package com.ldu.spring_blogcrud;

import com.ldu.spring_blogcrud.dto.PostRequestDto;
import com.ldu.spring_blogcrud.entity.Post;
import com.ldu.spring_blogcrud.repository.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringBlogCrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBlogCrudApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(PostRepository postRepository) {
        return (args) -> {
            for (int i = 0; i < 5; i++) {
                postRepository.save(new Post("제목1", "아이디1", "1234", "컨텐츠"));
            }
        };
    }
}
