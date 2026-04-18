package com.english_study.exception;


import com.english_study.model.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(UserNotFoundException.class)
    public ApiResponse<?> handleUserNotFound(UserNotFoundException ex) {
        log.warn("USER_NOT_FOUND: {}", ex.getMessage(), ex);

        return ApiResponse.error(
                HttpStatus.NOT_FOUND.value(),
                "USER_NOT_FOUND",
                ex.getMessage()
        );
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ApiResponse<?> handleUserExists(UserAlreadyExistsException ex) {
        log.warn("USER_ALREADY_EXISTS: {}", ex.getMessage(), ex);

        return ApiResponse.error(
                HttpStatus.BAD_REQUEST.value(),
                "USER_ALREADY_EXISTS",
                ex.getMessage()
        );
    }

    @ExceptionHandler(InvalidCredentialException.class)
    public ApiResponse<?> handleInvalidCredential(InvalidCredentialException ex) {
        log.warn("INVALID_CREDENTIAL: {}", ex.getMessage(), ex);

        return ApiResponse.error(
                HttpStatus.UNAUTHORIZED.value(),
                "INVALID_CREDENTIAL",
                ex.getMessage()
        );
    }

    // 🔥 Catch tất cả còn lại (chỉ cần 1 cái này)
    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleException(Exception ex) {
        log.error("UNEXPECTED_ERROR: {}", ex.getMessage(), ex);

        return ApiResponse.error(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_SERVER_ERROR",
                "Something went wrong"
        );
    }
}