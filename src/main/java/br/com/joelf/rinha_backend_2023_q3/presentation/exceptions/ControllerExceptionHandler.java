package br.com.joelf.rinha_backend_2023_q3.presentation.exceptions;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.joelf.rinha_backend_2023_q3.application.commons.ValidationConstants;
import br.com.joelf.rinha_backend_2023_q3.infrastructure.database.exceptions.EntityNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        boolean isBadRequest = ex.getFieldErrors().stream().anyMatch(violation -> 
            violation.getDefaultMessage().contains(ValidationConstants.MSG_INVALID_NAME) || violation.getDefaultMessage().contains(ValidationConstants.MSG_INVALID_STACK)
        );

        if (isBadRequest) {
            status = HttpStatus.BAD_REQUEST;
        }

        return ResponseEntity.status(status).body(ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Object> handleDuplicateKeyException(DuplicateKeyException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }
}
