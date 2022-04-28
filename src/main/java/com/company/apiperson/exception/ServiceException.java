package com.company.apiperson.exception;

import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException {

    private HttpStatus httpStatus;

    public ServiceException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}