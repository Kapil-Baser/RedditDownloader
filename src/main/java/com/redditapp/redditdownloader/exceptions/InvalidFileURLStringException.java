package com.redditapp.redditdownloader.exceptions;

public class InvalidFileURLStringException extends RuntimeException {
    public InvalidFileURLStringException(String message) {
        super(message);
    }

    public InvalidFileURLStringException(String message, Throwable cause) {
        super(message, cause);
    }
}
