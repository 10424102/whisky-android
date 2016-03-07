package org.team10424102.whisky.controllers;

public class AuthFailureException extends RuntimeException {
    public AuthFailureException() {
    }

    public AuthFailureException(String detailMessage) {
        super(detailMessage);
    }

    public AuthFailureException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public AuthFailureException(Throwable throwable) {
        super(throwable);
    }
}
