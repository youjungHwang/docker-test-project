package com.snsprojectredis.model.user.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snsprojectredis.model.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRedisTemplateService { // user데이터를 DB에서 조회하는게 아닌 redis에서 가져옴
    private static final String CACHE_KEY = "USER";

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private HashOperations<String, String, String> hashOperations; // CACHE_KEY, PK, DTO를 매퍼통해 json으로 저장

    @PostConstruct
    public void init() {
        this.hashOperations = redisTemplate.opsForHash(); //해시 알고리즘 사용
    }

    // redis에 저장
    public void save(UserDto userDto) {
        if(Objects.isNull(userDto) || Objects.isNull(userDto.getId())) {
            log.error("Required Values must not be null");
            return;
        }

        try {
            hashOperations.put(CACHE_KEY,
                    userDto.getId().toString(),
                    serializeUserDto(userDto));
            log.info("[UserRedisTemplateService save success] id: {}", userDto.getId());
        } catch (Exception e) {
            log.error("[UserRedisTemplateService save error] {}", e.getMessage());
        }
    }

    // redis로 전체조회
    public List<UserDto> findAll() {

        try {
            List<UserDto> list = new ArrayList<>();
            for (String value : hashOperations.entries(CACHE_KEY).values()) {
                UserDto pharmacyDto = deserializeUserDto(value);
                list.add(pharmacyDto);
            }
            return list;

        } catch (Exception e) {
            log.error("[UserRedisTemplateService findAll error]: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public void delete(Long id) {
        hashOperations.delete(CACHE_KEY, String.valueOf(id));
        log.info("[UserRedisTemplateService delete]: {} ", id);
    }


    // 직렬화 메서드 (객체를 스트링으로 변환)
    private String serializeUserDto(UserDto userDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(userDto);
    }

    private UserDto deserializeUserDto(String value) throws JsonProcessingException {
        return objectMapper.readValue(value, UserDto.class);
    }

}
