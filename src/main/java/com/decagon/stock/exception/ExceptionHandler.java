package com.decagon.stock.exception;

import com.decagon.stock.dto.response.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import java.util.Collections;

/**
 * @author Victor.Komolafe
 */
@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler({
            IllegalArgumentException.class,
            IllegalAccessException.class
    })
    public final ResponseEntity<Object> handleException(Exception ex, WebRequest request) {

        request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        ApiError apiError = new ApiError(HttpStatus.OK, ex.getMessage(), Collections.emptyList());

        return new ResponseEntity<>(apiError, apiError.getStatus());

    }

}
