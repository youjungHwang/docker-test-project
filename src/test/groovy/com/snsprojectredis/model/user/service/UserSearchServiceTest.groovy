package com.snsprojectredis.model.user.service

import com.snsprojectredis.model.user.cache.UserRedisTemplateService
import com.snsprojectredis.model.user.entity.User
import com.google.common.collect.Lists
import spock.lang.Specification

class UserSearchServiceTest extends Specification { // 단위테스트

    private UserSearchService userSearchService

    private  UserService userRepositoryService = Mock()
    private  UserRedisTemplateService userRedisTemplateService = Mock()

    private List<User> userList

    def setup(){
        userSearchService = new UserSearchService(userRepositoryService, userRedisTemplateService)

        userList = Lists.newArrayList(
                User.builder()
                        .id(1L)
                        .userName("김철수")
                        .build(),
                User.builder()
                        .id(2L)
                        .userName("고영희")
                        .build()
        )
    }

    def "레디스 장애시 DB를 이용 하여 유저 데이터 조회"() {

        when:
        userRedisTemplateService.findAll() >> []
        userRepositoryService.findAll() >> userList

        def result = userSearchService.searchUserDtoList()

        then:
        result.size() == 2
    }



}
