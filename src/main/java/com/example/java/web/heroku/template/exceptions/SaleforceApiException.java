package com.example.java.web.heroku.template.exceptions;

/**
 *
 *
 * @author Andres Canavesi
 */
public class SaleforceApiException extends Exception {

    /**
     *
     * @param message
     * @param throwable
     */
    public SaleforceApiException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
