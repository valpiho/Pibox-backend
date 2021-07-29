package com.pibox.core.exception.domain;

public class EmailExistException extends Exception{
    public EmailExistException(String message) {
        super(message);
    }
}
