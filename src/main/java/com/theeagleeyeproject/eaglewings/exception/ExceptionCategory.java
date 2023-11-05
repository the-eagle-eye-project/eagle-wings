package com.theeagleeyeproject.eaglewings.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCategory {

    VALIDATION_ERROR("Validation Error", HttpStatus.BAD_REQUEST),

    UNAUTHORIZED("Unauthorized Credentials", HttpStatus.UNAUTHORIZED),

    ACCOUNT_NOT_ACTIVE("Account is not active, please verify email first.", HttpStatus.UNAUTHORIZED),

    CONFLICT("Request has a conflict with our records.", HttpStatus.CONFLICT),

    METHOD_NOT_SUPPORTED("Resource not supported.", HttpStatus.METHOD_NOT_ALLOWED),

    DATA_NOT_FOUND("The data requested was not found in the platform database.", HttpStatus.NOT_FOUND),

    RUNTIME_ERROR("Runtime error", HttpStatus.INTERNAL_SERVER_ERROR),

    HEADER_ERROR("Required header is not present.", HttpStatus.BAD_REQUEST),

    THIRD_PARTY_APP_ERROR("Third party application is not responding or working as expected.", HttpStatus.SERVICE_UNAVAILABLE),

    FORBIDDEN("Credentials/Token is not valid", HttpStatus.FORBIDDEN);

    private final String description;
    private final HttpStatus httpStatus;

    ExceptionCategory(final String s, final HttpStatus httpStatus) {
        this.description = s;
        this.httpStatus = httpStatus;
    }
}