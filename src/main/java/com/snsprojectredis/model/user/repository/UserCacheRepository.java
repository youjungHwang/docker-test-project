package com.snsprojectredis.model.user.repository;

import com.snsprojectredis.config.RedisConfig;
import com.snsprojectredis.model.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserCacheRepository { // redis는 만료 시간 정하기 - 서비스 부하 시킴, 공간을 효율적으로 활용
    private final RedisTemplate<String, User> redisTemplate;
    private final static Duration USER_CACHE_TTL = Duration.ofDays(3);


    public void setUser(User user) {
        String key = getKey(user.getUserName());
        log.info("Set User to Redis {}({})", key, user);
        redisTemplate.opsForValue().set(key, user);
    }

    public Optional<User> getUser(String userName) {
        User data = redisTemplate.opsForValue().get(getKey(userName));
        log.info("Get User from Redis {}", data);
        return Optional.ofNullable(data);
    }

    private String getKey(String userName) {
        return "USER:" + userName;
    }

}
