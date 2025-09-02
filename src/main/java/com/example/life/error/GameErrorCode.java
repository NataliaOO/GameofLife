package com.example.life.error;

public enum GameErrorCode {
    INVALID_FILE(1, "Invalid or unreadable file"),
    INVALID_DIMENSIONS(2, "Field size mismatch"),
    INVALID_FORMAT(3, "File format is invalid"),
    OUT_OF_BOUNDS(4, "Cell coordinates out of bounds"),

    UNKNOWN_ERROR(99, "Unknown error");

    private final int code;
    private final String message;

    GameErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() { return code; }
    public String getMessage() { return message; }
}
