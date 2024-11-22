package com.example.metapor.common.exception;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ErrorCode {
    AUTH_SUCCESS(200, "AUTH_SUCCESS", "인증 성공"),
    UNAUTHORIZED(401, "UNAUTHORIZED", "인증되지 않았습니다."),
    FORBIDDEN(403, "FORBIDDEN", "권한이 없습니다."),
    USER_NOT_FOUND(401, "UE001", "사용자를 찾을 수 없습니다."),
    // ======================================= TOKEN =======================================
    TOKEN_NOT_FOUND(401, "TE001", "토큰이 존재하지 않습니다."),
    TOKEN_EXPIRED(401, "TE003", "만료된 토큰입니다."),
    TOKEN_NOT_SUPPORTED(401, "TE004", "지원하지 않는 토큰입니다."),
    TOKEN_NOT_VALID(401, "TE005", "유효하지 않은 토큰입니다."),
    REFRESH_TOKEN_EXPIRED(401, "TE006", "만료된 refresh 토큰입니다."),
    // ======================================= USER =======================================
    USER_ALREADY_EXIST(400, "UE002", "이미 존재하는 사용자입니다."),
    DOCTOR_NOT_FOUND(400, "UE003", "의사를 찾을 수 없습니다."),
    NOT_DOCTOR(400, "UE004", "의사가 아닙니다."),
    NOT_PATIENT(400, "UE005", "환자가 아닙니다."),


    // ======================================= EVENT =======================================
    EVENT_NOT_FOUND(400, "EE001", "이벤트를 찾을 수 없습니다."),

    INVALID_PASSWORD(400, "UE006", "비밀번호가 유효하지 않습니다."),

    ;
    private final String message;
    private final String code;
    private final int status;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public static ErrorCode of(String code) {
        return Arrays.stream(values())
                .filter(errorCode -> errorCode.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ErrorCode 입니다."));
    }
}
