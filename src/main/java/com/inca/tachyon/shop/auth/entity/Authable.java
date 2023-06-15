package com.inca.tachyon.shop.auth.entity;

import java.util.List;

/**
 * 로그인 가능한 유저 인터페이스
 * @author Sangwoo Joo
 */
public interface Authable {

    /**
     * 이메일 정보를 가져온다.
     * @return 이메일
     */
    String getEmail();

    List<Roleable> getRoles();

    String getPassword();
}
