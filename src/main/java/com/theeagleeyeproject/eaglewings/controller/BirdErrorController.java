package com.theeagleeyeproject.eaglewings.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * {@link BirdErrorController} is a controller designed to handle exceptions that are thrown outside Spring MVC.
 * <p>
 * For example: Java Filters
 *
 * @author John Robert Martinez Ponce
 */
@Controller
public class BirdErrorController {

    public static final String EXCEPTION_MAPPING = "/bird_error";

    @RequestMapping(EXCEPTION_MAPPING)
    public void handleError(HttpServletRequest request) {
        throw (RuntimeException) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
    }
}
