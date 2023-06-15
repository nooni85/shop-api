package com.inca.tachyon.shop.app.role.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository {
    int insertAuthority(@Param("role_no") long roleNo, @Param("user_no") long userNo);
}
