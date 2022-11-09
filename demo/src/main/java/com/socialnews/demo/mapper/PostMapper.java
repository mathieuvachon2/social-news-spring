package com.socialnews.demo.mapper;

import com.socialnews.demo.dto.PostRequest;
import com.socialnews.demo.dto.PostResponse;
import com.socialnews.demo.model.Community;
import com.socialnews.demo.model.Post;
import com.socialnews.demo.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "community", source = "community")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "description", source = "postRequest.description")
    Post map(PostRequest postRequest, Community community, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "communityName", source = "community.name")
    @Mapping(target = "userName", source = "user.username")
    PostResponse mapToDto(Post post);
}
