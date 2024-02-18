package com.springboot.bookstore.exception;

import org.springframework.http.HttpStatus;


public class BookStoreException extends RuntimeException {

    HttpStatus status;
    String msg;

    public BookStoreException(HttpStatus status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public BookStoreException(String message, HttpStatus status, String msg) {
        super(message);
        this.status = status;
        this.msg = msg;
    }

    public BookStoreException(String message, Throwable cause, HttpStatus status, String msg) {
        super(message, cause);
        this.status = status;
        this.msg = msg;
    }

    public BookStoreException(Throwable cause, HttpStatus status, String msg) {
        super(cause);
        this.status = status;
        this.msg = msg;
    }

    public BookStoreException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus status, String msg) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.status = status;
        this.msg = msg;
    }
}
