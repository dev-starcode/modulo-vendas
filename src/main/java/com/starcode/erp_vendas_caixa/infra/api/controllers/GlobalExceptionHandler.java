package com.starcode.erp_vendas_caixa.infra.api.controllers;

import com.starcode.erp_vendas_caixa.domain.exceptions.DomainException;
import com.starcode.erp_vendas_caixa.domain.exceptions.NotFoundException;
import jakarta.persistence.PersistenceException;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.concurrent.TimeoutException;
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

    @ExceptionHandler(value = DataAccessResourceFailureException.class)
    public ResponseEntity<?> handleDataAccessResource(final DataAccessResourceFailureException ex){
        final var response = GenericError.genericError("DataAccessResourceFailureException", ex.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    @ExceptionHandler(value = JDBCConnectionException.class)
    public ResponseEntity<?> handleJDBCConnectionException(final JDBCConnectionException ex){
        final var response = GenericError.genericError("JDBCConnectionException", ex.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(final ConstraintViolationException ex){
        final var response = GenericError.genericError("ConstraintViolationException", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @ExceptionHandler(value = TimeoutException.class)
    public ResponseEntity<?> handleTimeoutException(final TimeoutException ex){
        final var response = GenericError.genericError("TimeoutException", "O tempo limite foi atingido: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(response);
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException ex){
        final var response = GenericError.genericError("MethodArgumentTypeMismatchException", "Erro no tipo de argumento: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = NoHandlerFoundException.class)
    public ResponseEntity<?> handleNoHandlerFoundException(final NoHandlerFoundException ex){
        final var response = GenericError.genericError("NoHandlerFoundException", "Rota não encontrada: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(value = PersistenceException.class)
    public ResponseEntity<?> handlePersistenceException(final PersistenceException ex) {
        final var response = GenericError.genericError("PersistenceException", "Erro ao acessar o banco de dados: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    @ExceptionHandler(value = InvalidDataAccessResourceUsageException.class)
    public ResponseEntity<?> handleInvalidDataAccessResourceUsageException(final InvalidDataAccessResourceUsageException ex){
        final var response = GenericError.genericError("InvalidDataAccessResourceUsageException", "Erro ao acessar recurso do banco");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = HibernateException.class)
    public ResponseEntity<?> handleHibernateException(final HibernateException ex){
        final var response = GenericError.genericError("HibernateException", ex.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handleInternalServerError(final Exception ex) {
        final var response = GenericError.genericError("InternalServerError", "Erro desconhecido");
        return ResponseEntity
                .internalServerError()
                .body(response);
    }

    record ApiErrorDomain(String error, String message, String timestamp){
        static ApiErrorDomain create(final DomainException ex){
            return new ApiErrorDomain(ex.getTypeError(), ex.getError(), LocalDateTime.now().toString());
        }
    }

    record GenericError(String errorType, String message, String timestamp){
        static GenericError genericError(final String errorType, final String message){
            return new GenericError(errorType, message,  LocalDateTime.now().toString());
        }
    }
}
