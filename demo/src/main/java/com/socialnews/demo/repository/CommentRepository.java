package com.socialnews.demo.repository;

import com.socialnews.demo.model.Comment;
import com.socialnews.demo.model.Post;
import com.socialnews.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
