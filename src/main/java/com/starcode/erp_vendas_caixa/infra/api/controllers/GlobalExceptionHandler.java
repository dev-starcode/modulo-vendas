package com.starcode.erp_vendas_caixa.infra.api.controllers;

import com.starcode.erp_vendas_caixa.domain.exceptions.DomainException;
import com.starcode.erp_vendas_caixa.domain.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = DomainException.class)
    public ResponseEntity<?> handleDomainException(final DomainException ex){
        return ResponseEntity.unprocessableEntity().body(ApiErrorDomain.create(ex));
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(final NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiErrorDomain.create(ex));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        var error = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + " " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));
        var response = GenericError.genericError("UnprocessableEntity", error);
        return ResponseEntity.unprocessableEntity().body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleBadRequest(HttpMessageNotReadableException ex) {
        final var response = GenericError
                .genericError("BadRequest", "Erro de formatação no JSON enviado");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handleGeneralException(final Exception ex) {
        final var response = GenericError.genericError("InternalServerError", "Error desconhecido");
        return ResponseEntity
                .internalServerError()
                .body(response);
    }

    record ApiErrorDomain(String error, String message){
        static ApiErrorDomain create(final DomainException ex){
            return new ApiErrorDomain(ex.getTypeError(), ex.getError());
        }
    }

    record ApiError(String error, String message){
        static ApiError create(final Exception ex){
            return new ApiError(ex.getMessage(), ex.getLocalizedMessage());
        }
    }

    record GenericError( String errorType,  String message){
        static GenericError genericError(final String errorType,  final String message){
            return new GenericError(errorType, message);
        }
    }
}
