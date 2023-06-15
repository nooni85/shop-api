package com.inca.tachyon.shop.app.user.service;

import com.inca.tachyon.shop.app.role.entity.Role;
import com.inca.tachyon.shop.app.role.repository.AuthorityRepository;
import com.inca.tachyon.shop.app.role.repository.RoleRepository;
import com.inca.tachyon.shop.app.user.entity.User;
import com.inca.tachyon.shop.app.user.repository.UserRepository;
import com.inca.tachyon.shop.exception.ExceptionCode;
import com.inca.tachyon.shop.exception.TachyonShopException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class GrantService {
    /**
     * 관리자 기본 권한
     */
    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    /**
     * 사용자 기본 권한
     */
    private static final String ROLE_USER = "ROLE_USER";

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    @Transactional(propagation = Propagation.MANDATORY)
    public User grantAsUser(User user) {
        Role resultRole = roleRepository.selectRole(ROLE_USER);
        User resultUser = userRepository.selectUserOne(user.getEmail())
                .orElseThrow(()-> new TachyonShopException(ExceptionCode.USER_NOT_FOUND));

        authorityRepository.insertAuthority(resultRole.getRoleNo(), resultUser.getUserNo());

        return resultUser;
    }
}
