package com._line.CustomerAccountService.exceptions;

import java.io.IOException;

/**
 * this class handles messages displayed if a Bad Exception is thrown.
 */
public class BadRequestException extends IOException {
    private static final long serialVersionUID = 1L;

    private String message;

    public BadRequestException(boolean status, String message, String data) {
        this.message = message;
    }
    public BadRequestException(String message) {
        super(message);
        this.message = message;
    }


    public BadRequestException() {
        super();
    }
}
