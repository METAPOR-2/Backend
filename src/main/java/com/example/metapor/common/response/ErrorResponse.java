package com.example.metapor.common.response;

import com.example.metapor.common.exception.ErrorCode;
import lombok.Data;

@Data
public class ErrorResponse {
    private int status;
    private String code;
    private String message;

    public ErrorResponse(ErrorCode errorCode) {

        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
        this.status = errorCode.getStatus();
    }

    public void addMessage(String message) {
        this.message = this.message + ":" + message;
    }
}