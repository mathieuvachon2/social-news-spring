package com.socialnews.demo.repository;

import com.socialnews.demo.model.Community;
import com.socialnews.demo.model.Post;
import com.socialnews.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByCommunity(Community community);

    List<Post> findByUser(User user);
}
