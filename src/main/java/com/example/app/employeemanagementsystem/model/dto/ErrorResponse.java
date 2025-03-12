package com.example.app.employeemanagementsystem.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse {
    private int status;
    private String message;
    private String error;

    public ErrorResponse(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }

    // Static method to build ErrorResponse for exceptions
    public static ErrorResponse buildErrorResponse(int status, String error, String message) {
        return new ErrorResponse(status, error, message);
    }
}
