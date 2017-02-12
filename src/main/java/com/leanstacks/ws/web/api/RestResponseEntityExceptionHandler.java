package com.leanstacks.ws.web.api;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * A <code>@ControllerAdvice</code> class which provides exception handling to all REST controllers.
 * 
 * @author Matt Warman
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    /**
     * Handles JPA NoResultExceptions thrown from web service controller methods. Creates a response with an empty body
     * and HTTP status code 404, not found.
     * 
     * @param ex A NoResultException instance.
     * @return A ResponseEntity with an empty response body and HTTP status code 404.
     */
    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<Object> handleNoResultException(final NoResultException ex, final WebRequest request) {
        logger.info("> handleNoResultException");
        logger.info("- NoResultException: ", ex);
        final ExceptionDetail detail = new ExceptionDetailBuilder().exception(ex).httpStatus(HttpStatus.NOT_FOUND)
                .webRequest(request).build();
        logger.info("< handleNoResultException");
        return handleExceptionInternal(ex, detail, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    /**
     * Handles EmptyResultDataAccessException thrown from web service controller methods. Creates a response with an
     * empty body and HTTP status code 404, not found.
     * 
     * @param ex An EmptyResultDataAccessException instance.
     * @return A ResponseEntity with an empty response body and HTTP status code 404.
     */
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Object> handleEmptyResultDataAccessException(final EmptyResultDataAccessException ex,
            final WebRequest request) {
        logger.info("> handleEmptyResultDataAccessException");
        logger.warn("- EmptyResultDataAccessException: ", ex);
        final ExceptionDetail detail = new ExceptionDetailBuilder().exception(ex).httpStatus(HttpStatus.NOT_FOUND)
                .webRequest(request).build();
        logger.info("< handleEmptyResultDataAccessException");
        return handleExceptionInternal(ex, detail, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    /**
     * Handles all Exceptions not addressed by more specific <code>@ExceptionHandler</code> methods. Creates a response
     * with the Exception detail in the response body as JSON and a HTTP status code of 500, internal server error.
     * 
     * @param ex An Exception instance.
     * @return A ResponseEntity containing a the Exception attributes in the response body and a HTTP status code 500.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(final Exception ex, final WebRequest request) {
        logger.info("> handleException");
        logger.error("- Exception: ", ex);
        final ExceptionDetail detail = new ExceptionDetailBuilder().exception(ex)
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR).webRequest(request).build();
        logger.info("< handleException");
        return handleExceptionInternal(ex, detail, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
