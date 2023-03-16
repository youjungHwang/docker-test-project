package com.snsprojectredis.model.user.cache

import com.snsprojectredis.AbstractIntegrationContainerBaseTest
import com.snsprojectredis.model.user.dto.UserDto
import org.springframework.beans.factory.annotation.Autowired

class UserRedisTemplateServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private UserRedisTemplateService userRedisTemplateService

    def setup() {
        userRedisTemplateService.findAll()
                .forEach(dto -> {
                    userRedisTemplateService.delete(dto.getId())
                })
    }

    def "save success"() {
        given:
        String userName = "userName"
        String userAddress = "userAddress"
        UserDto dto =
                UserDto.builder()
                        .id(1L)
                        .userName(userName)
                        .userAddress(userAddress)
                        .build()

        when:
        userRedisTemplateService.save(dto)
        List<UserDto> result = userRedisTemplateService.findAll()

        then:
        result.size() == 1
        result.get(0).id == 1L
        result.get(0).userName == userName
        result.get(0).userAddress == userAddress
    }

    def "success fail"() {
        given:
        UserDto dto =
            UserDto.builder()
                    .build()

        when:
        userRedisTemplateService.save(dto)
        List<UserDto> result = userRedisTemplateService.findAll()

        then:
        result.size() == 0
    }

    def "delete"() {
        given:
        String userName = "userNameD"
        String userAddress = "userAddressD"
        UserDto dto =
            UserDto.builder()
                    .id(1L)
                    .userName(userName)
                    .userAddress(userAddress)
                    .build()

        when:
        userRedisTemplateService.save(dto)
        userRedisTemplateService.delete(dto.getId())
        def result = userRedisTemplateService.findAll()

        then:
        result.size() == 0
    }
}
