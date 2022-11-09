package com.socialnews.demo.service;

import com.socialnews.demo.dto.PostRequest;
import com.socialnews.demo.dto.PostResponse;
import com.socialnews.demo.exceptions.CommunityNotFoundException;
import com.socialnews.demo.exceptions.PostNotFoundException;
import com.socialnews.demo.exceptions.SocialNewsException;
import com.socialnews.demo.mapper.PostMapper;
import com.socialnews.demo.model.Community;
import com.socialnews.demo.model.Post;
import com.socialnews.demo.model.User;
import com.socialnews.demo.repository.CommunityRepository;
import com.socialnews.demo.repository.PostRepository;
import com.socialnews.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final CommunityRepository communityRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;
    public void save(PostRequest postRequest) {
        Community community = communityRepository.findByName(postRequest.getCommunityName())
                .orElseThrow(() -> new SocialNewsException(postRequest.getCommunityName()));
        User currentUser = authService.getCurrentUser();

        postRepository.save(postMapper.map(postRequest, community, currentUser));
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByCommunity(Long communityId) {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new CommunityNotFoundException(communityId.toString()));
        List<Post> posts = postRepository.findAllByCommunity(community);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

}
