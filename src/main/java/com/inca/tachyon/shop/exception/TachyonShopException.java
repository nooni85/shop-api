package com.inca.tachyon.shop.exception;

import lombok.Getter;

@Getter
public class TachyonShopException extends RuntimeException {
    private final ExceptionCode exceptionCode;

    public TachyonShopException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
