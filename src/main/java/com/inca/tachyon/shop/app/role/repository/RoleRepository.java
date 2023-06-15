package com.inca.tachyon.shop.app.role.repository;

import com.inca.tachyon.shop.app.role.entity.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository {
    Role selectRole(String name);
}
