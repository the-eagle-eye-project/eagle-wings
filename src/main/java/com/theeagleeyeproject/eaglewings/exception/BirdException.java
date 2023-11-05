package com.theeagleeyeproject.eaglewings.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class BirdException extends RuntimeException {

    /**
     * ENUM that contains all the runtime error categories and will contain standardized information about the exception.
     */
    private ExceptionCategory exceptionCategory;

    /**
     * Custom message created by the developer to describe a specific Exception.
     */
    private String developerMessage;

    /**
     * Error stack trace from the method that failed.
     */
    private String traceMessage;

    public BirdException(ExceptionCategory exceptionCategory, String developerMessage) {
        this.exceptionCategory = exceptionCategory;
        this.developerMessage = developerMessage;
    }
}
