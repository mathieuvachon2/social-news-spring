package com.socialnews.demo.service;

import com.socialnews.demo.dto.CommunityDto;
import com.socialnews.demo.exceptions.SocialNewsException;
import com.socialnews.demo.mapper.CommunityMapper;
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
    private final CommunityMapper communityMapper;

    @Transactional
    public CommunityDto save(CommunityDto communityDto) {
        Community save = communityRepository.save(communityMapper.mapDtoToCommunity(communityDto));
        communityDto.setId(save.getId());
        return communityDto;
    }

    @Transactional(readOnly = true)
    public List<CommunityDto> getAll() {
        return communityRepository.findAll()
                .stream()
                .map(communityMapper::mapCommunityToDto)
                .collect(Collectors.toList());
    }

    public CommunityDto getCommunity(Long id) {
        Community community = communityRepository.findById(id)
                .orElseThrow(() -> new SocialNewsException("No community found with with that id"));
        return communityMapper.mapCommunityToDto(community);
    }
}
