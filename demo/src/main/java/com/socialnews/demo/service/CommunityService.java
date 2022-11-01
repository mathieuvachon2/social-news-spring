package com.socialnews.demo.service;

import com.socialnews.demo.dto.CommunityDto;
import com.socialnews.demo.model.Community;
import com.socialnews.demo.repository.CommunityRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CommunityService {

    private final CommunityRepository communityRepository;

    @Transactional
    public CommunityDto save(CommunityDto communityDto) {
        Community save = communityRepository.save(mapCommunityDto(communityDto));
        communityDto.setId(save.getId());
        return communityDto;
    }

    @Transactional(readOnly = true)
    public List<CommunityDto> getAll() {
        return communityRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private CommunityDto mapToDto(Community community) {
        return CommunityDto.builder().name(community.getName()).id(community.getId())
                .numberPosts(community.getPosts().size())
                .build();
    }

    private Community mapCommunityDto(CommunityDto communityDto) {
        return Community.builder().name(communityDto.getName())
                .description(communityDto.getDescription())
                .build();
    }
}
