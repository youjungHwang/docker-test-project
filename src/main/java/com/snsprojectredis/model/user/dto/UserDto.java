package com.snsprojectredis.model.user.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto { // 어디서는 Dto를 request로 쓰기도 한다.

    private Long id;
    private String userName;
}
