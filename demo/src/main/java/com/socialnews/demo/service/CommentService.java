package com.socialnews.demo.service;

import com.socialnews.demo.dto.CommentsDto;
import com.socialnews.demo.exceptions.PostNotFoundException;
import com.socialnews.demo.mapper.CommentMapper;
import com.socialnews.demo.model.Comment;
import com.socialnews.demo.model.Post;
import com.socialnews.demo.model.User;
import com.socialnews.demo.repository.CommentRepository;
import com.socialnews.demo.repository.PostRepository;
import com.socialnews.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private CommentRepository commentRepository;
    private final MailContentBuilder mailContentBuilder;

    public void save(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId()).orElseThrow(() ->
                new PostNotFoundException(commentsDto.getPostId().toString()));

        Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

        // TODO add mail to the comment's post user
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new PostNotFoundException(postId.toString())
        );

        return commentRepository.findByPost(post).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
    }

    public List<CommentsDto> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException(userName));

        return commentRepository.findAllByUser(user).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
    }
}
