package com._line.CustomerAccountService.response;

import lombok.Data;

@Data
public class JSENDResponse<T> {
    private boolean isSuccessful;
    private String message;
    private T data;

    public JSENDResponse(boolean isSuccessful, T data, String message) {
        this.isSuccessful = isSuccessful;
        this.data = data;
        this.message = message;
    }

    // Static methods for success and error
    public static <T> JSENDResponse<T> success(T data, String message) {
        return new JSENDResponse<>(true, data, message);
    }

    public static <T> JSENDResponse<T> fail(String message) {
        return new JSENDResponse<>(false, null, message);
    }

    // Getters and setters
    public boolean getIsSuccessful() {
        return isSuccessful;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}