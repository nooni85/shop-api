package com.inca.tachyon.shop.auth.userdetails;

import com.inca.tachyon.shop.app.user.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Optional;

public class TachyonUserDetails extends User implements UserDetails {
    public TachyonUserDetails(User user) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(user, this);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Optional.ofNullable(super.getRoles()).stream()
                .map(roleable -> new SimpleGrantedAuthority("ROLE_"+roleable))
                .toList();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
