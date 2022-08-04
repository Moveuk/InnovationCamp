package com.ldu.spring_blogcrud;

import com.ldu.spring_blogcrud.entity.Post;
import com.ldu.spring_blogcrud.entity.Reply;
import com.ldu.spring_blogcrud.entity.User;
import com.ldu.spring_blogcrud.repository.PostRepository;
import com.ldu.spring_blogcrud.repository.ReplyRepository;
import com.ldu.spring_blogcrud.repository.UserRepository;
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
    public CommandLineRunner demo(PostRepository postRepository, ReplyRepository replyRepository, UserRepository userRepository) {
        return (args) -> {
            for (int i = 0; i < 5; i++) {
                postRepository.save(new Post("제목"+(i+1), "tester"+(i+1), "1234", "컨텐츠"+(i+1)));
            }
            for (int i = 0; i < 3; i++) {
                userRepository.save(new User("tester"+(i+1), "$2a$10$MdJ.l1OfONuzbi2mzi4STuK4ojCw1748TdZgZqb5Q7f4oqfAeH3MG"));
            }
//            for (int i = 0; i < 3; i++) {
//                replyRepository.save(new Reply(2L,"tester"+(i+1), "댓글입니다."));
//            }
//            for (int i = 0; i < 3; i++) {
//                replyRepository.save(new Reply(3L,"tester"+(i+1), "댓글입니다."));
//            }
        };
    }
}
