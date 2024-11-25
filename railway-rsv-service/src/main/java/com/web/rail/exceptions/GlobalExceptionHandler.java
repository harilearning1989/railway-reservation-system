package com.web.rail.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.SQLSyntaxErrorException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Handle Oracle-specific database exception
    @ExceptionHandler(DatabaseOperationException.class)
    public ProblemDetail handleDatabaseOperationException(DatabaseOperationException ex, WebRequest request) {
        return getProblemDetails(ex.getMessage(), request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        return getProblemDetails(ex.getMessage(), request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return getProblemDetails(ex.getMessage(), request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        return getProblemDetails(ex.getMessage(), request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ProblemDetail handleMethodNotSupported(HttpRequestMethodNotSupportedException ex, WebRequest request) {
        return getProblemDetails("Method not supported", request, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ProblemDetail handleMethodNotSupported(MissingServletRequestParameterException ex, WebRequest request) {
        return getProblemDetails("Missing Method Parameter", request, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(SQLSyntaxErrorException.class)
    public ProblemDetail handleSQLSyntaxError(SQLSyntaxErrorException ex, WebRequest request) {
        return getProblemDetails(ex.getMessage(), request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ProblemDetail handleSQLSyntaxError(InvalidDataAccessApiUsageException ex, WebRequest request) {
        return getProblemDetails(ex.getMessage(), request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NullPointerException.class)
    public ProblemDetail handleNullPointerException(NullPointerException ex, WebRequest request) {
        return getProblemDetails(ex.getMessage(), request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleNullPointerException(RuntimeException ex, WebRequest request) {
        return getProblemDetails(ex.getMessage(), request, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ProblemDetail handleNoResourceFoundException(NoResourceFoundException ex, WebRequest request) {
        return getProblemDetails(ex.getMessage(), request, HttpStatus.NOT_FOUND);
    }

    private ProblemDetail getProblemDetails(String message, WebRequest request, HttpStatus httpStatus) {
        // Construct the base URL dynamically
        URI typeURI = ServletUriComponentsBuilder.fromCurrentRequestUri()
                //.replacePath("/internal-server-error")
                .build()
                .toUri();
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                httpStatus,
                "An unexpected error occurred"
        );

        String formattedTimestamp = LocalDateTime.now().format(FORMATTER);
        problemDetail.setProperty("timestamp", formattedTimestamp);
        problemDetail.setType(typeURI);
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setDetail(message);
        problemDetail.setProperty("path", request.getDescription(false));

        return problemDetail;
    }

}

