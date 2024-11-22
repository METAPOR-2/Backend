package com.example.metapor.common.exception;

import com.example.metapor.common.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ExceptionControllerHandler {
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> customException(CustomException e, HttpServletRequest request) {
        ErrorCode errorCode = e.getErrorCode();
        log.error("Request URI : [{}] {}", request.getMethod() ,request.getRequestURI());
        log.error("Error message : {}", errorCode.getMessage());

        return new ResponseEntity<>(new ErrorResponse(errorCode), HttpStatus.resolve(errorCode.getStatus()));
    }
}
