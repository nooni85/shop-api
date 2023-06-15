package com.inca.tachyon.shop.auth.userdetails;

import com.inca.tachyon.shop.app.user.entity.User;
import com.inca.tachyon.shop.app.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TachyonUsersDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.selectUserOne(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new TachyonUserDetails(user);
    }
}
