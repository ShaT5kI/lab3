package ru.nsu.minesweeper.exceptions;

public class ClassExistenceException extends Exception {
    public ClassExistenceException() {
    }

    public ClassExistenceException(String message) {
        super(message);
    }

    public ClassExistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
