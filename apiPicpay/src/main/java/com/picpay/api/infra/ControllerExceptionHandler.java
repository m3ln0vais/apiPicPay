package com.picpay.api.infra;

import com.picpay.api.dtos.ExceptionDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity threadDuplicateEntry(DataIntegrityViolationException exception){
        ExceptionDTO dto = new ExceptionDTO("Usuario ja cadastrado", "400");
        return ResponseEntity.badRequest().body(dto);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity thread404(EntityNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity threadGeneralExceptions(Exception exception) {
        ExceptionDTO dto = new ExceptionDTO(exception.getMessage(), "500");
        return ResponseEntity.internalServerError().body(dto);
    }
}
