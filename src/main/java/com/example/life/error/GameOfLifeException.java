package com.example.life.error;

public class GameOfLifeException extends RuntimeException {
    private final GameErrorCode errorCode;

    public GameOfLifeException(GameErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    public GameOfLifeException(GameErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }
    public GameErrorCode getErrorCode() { return errorCode; }
}
