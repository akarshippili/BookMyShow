package com.movieTicketService.userservice.exception;

public class MovieTicketException extends RuntimeException{
    public MovieTicketException() {
    }

    public MovieTicketException(String message) {
        super(message);
    }

    public MovieTicketException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieTicketException(Throwable cause) {
        super(cause);
    }

    public MovieTicketException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
