package com.inca.tachyon.shop.app.user.repository;

import com.inca.tachyon.shop.app.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {
    int insertUser(User user);

    Optional<User> selectUserOne(String email);

    Optional<User> selectUserOneWithRole(String email);
}
