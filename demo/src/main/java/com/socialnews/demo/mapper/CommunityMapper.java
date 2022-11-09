package com.socialnews.demo.mapper;

import com.socialnews.demo.dto.CommunityDto;
import com.socialnews.demo.model.Community;
import com.socialnews.demo.model.Post;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommunityMapper {

    @Mapping(target = "numberPosts", expression = "java(mapPosts(community.getPosts()))")
    CommunityDto mapCommunityToDto(Community community);

    default Integer mapPosts(List<Post> numberPosts) {
        return numberPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Community mapDtoToCommunity(CommunityDto communityDto);
}
