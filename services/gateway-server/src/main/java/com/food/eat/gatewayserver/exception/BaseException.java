package com.food.eat.gatewayserver.exception;

import lombok.Getter;
import lombok.Setter;

public class BaseException extends RuntimeException {

    @Getter
    @Setter
    private String errorCode;

    @Getter
    @Setter
    private String message;

    public BaseException(String message) {
        super(message);
        this.message = message;
    }
    public BaseException(String message, String errorCode) {
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }
}
