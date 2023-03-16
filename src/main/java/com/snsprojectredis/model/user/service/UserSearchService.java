package com.snsprojectredis.model.user.service;

import com.snsprojectredis.model.user.cache.UserRedisTemplateService;
import com.snsprojectredis.model.user.dto.UserDto;
import com.snsprojectredis.model.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserSearchService {
    // 의존성 주입 DI
    private final UserService userService;
    private final UserRedisTemplateService userRedisTemplateService;

    public List<UserDto> searchUserDtoList() { // 전체 유저 조회

        // redis에서 선 조회, 만약 문제가 발생시 그때 DB조회 (분기처리 - 테스트1)
        List<UserDto> userDtoList = userRedisTemplateService.findAll();
        if(!CollectionUtils.isEmpty(userDtoList)) return userDtoList;

        // db
        return userService.findAll()
                .stream()
                .map(this::convertToUserDto)
                .collect(Collectors.toList());
    }

    private UserDto convertToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .build();
    }
}
