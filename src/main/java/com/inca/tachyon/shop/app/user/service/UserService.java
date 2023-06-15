package com.inca.tachyon.shop.app.user.service;

import com.inca.tachyon.shop.app.role.repository.RoleRepository;
import com.inca.tachyon.shop.app.user.entity.User;
import com.inca.tachyon.shop.app.user.repository.UserRepository;
import com.inca.tachyon.shop.exception.ExceptionCode;
import com.inca.tachyon.shop.exception.TachyonShopException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final GrantService grantService;

    @Transactional(propagation = Propagation.REQUIRED)
    public int insertUser(User user) {
        userRepository.selectUserOne(user.getEmail())
                .ifPresent(m -> { throw new TachyonShopException(ExceptionCode.USER_EXISTS); });

        // 기본 롤을 입력한다.
        roleRepository.selectRole("ROLE_USER");

        // 비밀번호 암호화
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        int count = userRepository.insertUser(user);
        grantService.grantAsUser(user);

        return count;
    }

    public Optional<User> selectUserOne(String email) {
        return userRepository.selectUserOne(email);
    }
}
