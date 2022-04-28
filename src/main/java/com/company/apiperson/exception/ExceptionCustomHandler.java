package com.company.apiperson.exception;

import com.company.apiperson.model.response.ErrorMessage;
import com.company.apiperson.model.response.ErrorRS;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;

@ControllerAdvice
public class ExceptionCustomHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorRS> handleBindExceptionHandler(BindException exception) {
        List<ErrorMessage> errors = new ArrayList<>();
        if(!CollectionUtils.isEmpty(exception.getBindingResult().getAllErrors())) {
            for (ObjectError objError : exception.getBindingResult().getAllErrors()) {
                errors.add(new ErrorMessage(objError.getDefaultMessage()));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(new ErrorRS(errors));
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorRS> handleServiceExceptionHandler(ServiceException exception) {
        return ResponseEntity.status(exception.getHttpStatus())
                .body(new ErrorRS(Arrays.asList(new ErrorMessage(exception.getMessage()))));
    }

}
