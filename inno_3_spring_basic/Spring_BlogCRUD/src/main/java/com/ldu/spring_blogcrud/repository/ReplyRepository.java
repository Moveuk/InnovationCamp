package com.ldu.spring_blogcrud.repository;

import com.ldu.spring_blogcrud.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByPostIdOrderByCreatedAtDesc(Long postId);
}
