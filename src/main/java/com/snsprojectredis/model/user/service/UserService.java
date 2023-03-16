package com.snsprojectredis.model.user.service;

import com.snsprojectredis.exception.ErrorCode;
import com.snsprojectredis.exception.SnsCustomException;
import com.snsprojectredis.model.user.entity.User;
import com.snsprojectredis.model.user.repository.UserCacheRepository;
import com.snsprojectredis.model.user.repository.UserRepository;
import com.snsprojectredis.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserCacheRepository userCacheRepository;
    private final BCryptPasswordEncoder encoder;

    public User loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userCacheRepository.getUser(userName).orElseGet(
                () -> userRepository.findByUserName(userName).map(User::fromEntity).orElseThrow(
                        () -> new SnsCustomException(ErrorCode.USER_NOT_FOUND, String.format("userName is %s", userName))
                ));
    }

    public String login(String userName, String password) {
        User savedUser = loadUserByUsername(userName);
        userCacheRepository.setUser(savedUser);
        if (!encoder.matches(password, savedUser.getPassword())) {
            throw new SnsCustomException(ErrorCode.INVALID_PASSWORD);
        }
        //return JwtTokenUtils.generateAccessToken(userName, secretKey, expiredTimeMs);
        return "";
    }


    @Transactional
    public User join(String userName, String password) {
        // check the userId not exist
        userRepository.findByUserName(userName).ifPresent(it -> {
            throw new SnsCustomException(ErrorCode.DUPLICATED_USER_NAME, String.format("userName is %s", userName));
        });

        User savedUser = userRepository.save(User.of(userName, encoder.encode(password)));
        return User.fromEntity(savedUser);
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
