package com.ldu.spring_blogcrud.repository;

import com.ldu.spring_blogcrud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByNickname(String nickname);
}
