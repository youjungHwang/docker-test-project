package com.snsprojectredis.model.user.repository;

import com.snsprojectredis.model.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> { // CRUD 가능
    Optional<User> findByUserName(String userName);
}
