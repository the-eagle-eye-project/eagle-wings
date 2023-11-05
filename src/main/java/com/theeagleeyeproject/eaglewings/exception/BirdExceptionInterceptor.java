package com.theeagleeyeproject.eaglewings.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Controller Advice that manages all Exceptions that happens in the application.
 *
 * @author John Martinez
 */
@ControllerAdvice
public class BirdExceptionInterceptor extends ResponseEntityExceptionHandler {

    /**
     * Exception handler that will handle all custom exceptions thrown by the developer in specific scenarios.
     *
     * @param ex developer's info added when exception is thrown
     * @return ResponseEntity object with the ExceptionResponse back to the consumer
     */
    @ExceptionHandler(BirdException.class)
    public final ResponseEntity<Object> handleAllExceptions(BirdException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                ex.getExceptionCategory().getHttpStatus().value(),
                ex.getExceptionCategory(),
                ex.getExceptionCategory().getDescription(),
                ex.getDeveloperMessage());
        return new ResponseEntity<>(exceptionResponse, exceptionResponse.getExceptionCategory().getHttpStatus());
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public final ResponseEntity<Object> handleInternalAuthServiceExceptions(InternalAuthenticationServiceException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                ExceptionCategory.UNAUTHORIZED.getHttpStatus().value(),
                ExceptionCategory.UNAUTHORIZED,
                ExceptionCategory.UNAUTHORIZED.getDescription(),
                e.getMessage());
        return new ResponseEntity<>(exceptionResponse, exceptionResponse.getExceptionCategory().getHttpStatus());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                ExceptionCategory.UNAUTHORIZED.getHttpStatus().value(),
                ExceptionCategory.UNAUTHORIZED,
                ExceptionCategory.UNAUTHORIZED.getDescription(),
                e.getMessage());
        return new ResponseEntity<>(exceptionResponse, exceptionResponse.getExceptionCategory().getHttpStatus());
    }

    /**
     * Method that overrides handleMethodArgumentNotValid which is thrown when a Validation fails (Spring @Valid). This is mainly use for Consumer Request validation.
     *
     * @param ex      Exception message that comes from the validation exception thrown by @Valid
     * @param headers sent by the consumer used to extract the Transaction Key created in the Spring interceptor preHandle method.
     * @param status  NOT USED
     * @param request NOT USED
     * @return ResponseEntity object with the ExceptionResponse back to the consumer
     */
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                ExceptionCategory.VALIDATION_ERROR.getHttpStatus().value(),
                ExceptionCategory.VALIDATION_ERROR,
                ExceptionCategory.VALIDATION_ERROR.getDescription(),
                ex.getMessage()
        );

        return new ResponseEntity<>(response, response.getExceptionCategory().getHttpStatus());
    }

    /**
     * Method that overrides handleHttpMessageNotReadable from Object ResponseEntityExceptionHandler, which is used to be thrown when the ENUM validation fails.
     *
     * @param ex      Exception message that comes from the ENUM validation exception is thrown
     * @param headers sent by the consumer used to extract the Transaction Key created in the Spring interceptor preHandle method.
     * @param status  NOT USED
     * @param request NOT USED
     * @return ResponseEntity object with the ExceptionResponse back to the consumer
     */
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                ExceptionCategory.VALIDATION_ERROR.getHttpStatus().value(),
                ExceptionCategory.VALIDATION_ERROR,
                ExceptionCategory.VALIDATION_ERROR.getDescription(),
                ex.getMessage()
        );

        return new ResponseEntity<>(response, response.getExceptionCategory().getHttpStatus());
    }
}