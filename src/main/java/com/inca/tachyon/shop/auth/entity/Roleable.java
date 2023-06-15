package com.inca.tachyon.shop.auth.entity;

/**
 * 역활 부여가 가능한 사용자
 * @author Sangwoo Joo
 */
public interface Roleable {

    /**
     * 역활 이름을 가져온다.
     * @return 역활명
     */
    String getName();

    /**
     * 역활 고유 이름을 가져온다.
     * 이 값은 로직상 ID 값으로 쓰여진다.
     * @return 고유값
     */
}
