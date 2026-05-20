package com.ess.essreddit.exceptions;

public class EssRedditException extends RuntimeException{
    public EssRedditException(String message, Exception exception) {
        super(message, exception);
    }

    public EssRedditException(String message) {
        super(message);
    }
}
