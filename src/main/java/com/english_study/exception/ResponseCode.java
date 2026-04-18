package com.english_study.exception;

public enum ResponseCode {
    SUCCESS(200, null, "Success"),
    NOT_FOUND(404, "CATEGORY_NOT_FOUND", "Category not found"),
    VALIDATION_ERROR(400, "VALIDATION_ERROR", "Validation error"),
    SERVER_ERROR(500, "SERVER_ERROR", "Internal server error");

    private final int statusCode;
    private final String errorCode;
    private final String message;

    ResponseCode(int statusCode, String errorCode, String message) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.message = message;
    }

    public int statusCode() {
        return statusCode;
    }

    public String errorCode() {
        return errorCode;
    }

    public String message() {
        return message;
    }
}
