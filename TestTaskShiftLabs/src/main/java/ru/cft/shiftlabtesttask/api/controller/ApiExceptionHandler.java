package ru.cft.shiftlabtesttask.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.cft.shiftlabtesttask.core.exception.ServiceException;

import static ru.cft.shiftlabtesttask.core.exception.HttpStatuses.ITERNAL_SERVER_ERROR;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Object> handle(ServiceException ex) {
        return new ResponseEntity<>(ex.getLocalizedMessage(), ex.getErrorCode().get_status());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handle(RuntimeException ex) {
        return new ResponseEntity<>(ex.getLocalizedMessage(), ITERNAL_SERVER_ERROR);
    }
}
