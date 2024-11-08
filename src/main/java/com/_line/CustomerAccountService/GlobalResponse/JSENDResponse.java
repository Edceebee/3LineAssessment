package com._line.CustomerAccountService.GlobalResponse;

public class JSENDResponse<T> {
    private boolean status;
    private String message;
    private T data;

    public JSENDResponse(boolean status, T data, String message) {
        this.status = status;
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
    public boolean getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}