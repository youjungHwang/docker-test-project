package com.snsprojectredis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig { // 직렬화 역직렬화, 싱글톤

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
