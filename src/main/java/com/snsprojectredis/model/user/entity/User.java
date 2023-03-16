package com.snsprojectredis.model.user.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 키값 생성을 DB에 위임 - DB가 생성해서 매핑
    private Long id;

    private String userName;
    private String password;

    public static User of(String userName, String encodedPwd) {
        User userEntity = new User();
        userEntity.setUserName(userName);
        userEntity.setPassword(encodedPwd);
        return userEntity;
    }

    public static User fromEntity(User entity) {
        return new User(
                entity.getId(),
                entity.getUserName(),
                entity.getPassword()
        );
    }

}
