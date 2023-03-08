package com.snsprojectredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SnsProjectRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnsProjectRedisApplication.class, args);
	}

}
