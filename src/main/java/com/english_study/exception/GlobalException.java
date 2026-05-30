package com.english_study.exception;


import com.english_study.model.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiResponse<?> handleResourceNotFound(ResourceNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ApiResponse.error(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ApiResponse<?> handleUserNotFound(UserNotFoundException ex) {
        log.error("USER_NOT_FOUND: {}", ex.getMessage(), ex);

        return ApiResponse.error(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ApiResponse<?> handleUserExists(UserAlreadyExistsException ex) {
        log.error("USER_ALREADY_EXISTS: {}", ex.getMessage(), ex);

        return ApiResponse.error(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()
        );
    }

    @ExceptionHandler(InvalidCredentialException.class)
    public ApiResponse<?> handleInvalidCredential(InvalidCredentialException ex) {
        log.error("INVALID_CREDENTIAL: {}", ex.getMessage(), ex);

        return ApiResponse.error(
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage()
        );
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("BAD_REQUEST: {}", ex.getMessage(), ex);

        return ApiResponse.error(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()
        );
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleException(Exception ex) {
        log.error("UNEXPECTED_ERROR: {}", ex.getMessage(), ex);

        return ApiResponse.error(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Something went wrong"
        );
    }
}