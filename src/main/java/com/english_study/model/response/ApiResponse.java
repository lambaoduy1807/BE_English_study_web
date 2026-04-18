package com.english_study.model.response;

public record ApiResponse<T>(
        int statusCode,
        String errorCode,
        String message,
        T data
) {
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(200, null, message, data);
    }

    public static <T> ApiResponse<T> error(int code, String errorCode, String message) {
        return new ApiResponse<>(code, errorCode, message, null);
    }
}
