package com.snsprojectredis.model.user.controller;

import com.snsprojectredis.model.user.cache.UserRedisTemplateService;
import com.snsprojectredis.model.user.dto.UserDto;
import com.snsprojectredis.model.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userRepositoryService;
    private final UserRedisTemplateService userRedisTemplateService;

    // 데이터 초기 셋팅을 위한 임시 메소드 (DB 데이터를 redis 로 가지고 오는 동기화 메서드)
    // 유저 데이터를 UserDto로 converting해줌
    @GetMapping("/redis/save")
    public String save() {
        List<UserDto> userDtoList = userRepositoryService.findAll()
                .stream().map(user -> UserDto.builder()
                        .id(user.getId())
                        .userName(user.getUserName())
                        .build())
                .collect(Collectors.toList());

        userDtoList.forEach(userRedisTemplateService::save);

        return "success save";
    }
}
