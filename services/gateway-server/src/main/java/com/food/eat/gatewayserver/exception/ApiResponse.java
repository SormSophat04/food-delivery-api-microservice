package com.food.eat.gatewayserver.exception;

public record ApiResponse<T>(
        boolean success,
        String errorCode,
        String message,
        T data
) {
    public ApiResponse(boolean success, String errorCode, String message, T data) {
        this.success = success;
        this.errorCode = errorCode;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "200", "Request was successful", data);
    }

    public static <T> ApiResponse<T> error(String errorCode, String message) {
        return new ApiResponse<>(false, errorCode, message, null);
    }
}
