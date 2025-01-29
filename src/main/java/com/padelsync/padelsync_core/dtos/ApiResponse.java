package com.padelsync.padelsync_core.dtos;

public class ApiResponse<T> {
    
    private T data;
    private String message;
    private String status;

    public ApiResponse(T data, String message, String status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }
}
