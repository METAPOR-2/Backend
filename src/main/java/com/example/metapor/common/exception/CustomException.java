package com.example.metapor.common.exception;

public class CustomException extends Exception {
    // Exception 메세지 설정
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public static CustomException of(ErrorCode errorCode) {
        return new CustomException(errorCode);
    }

}
