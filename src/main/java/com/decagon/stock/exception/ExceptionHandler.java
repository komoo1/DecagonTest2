package com.decagon.stock.exception;

import com.decagon.stock.dto.response.ApiResponse;
import com.decagon.stock.dto.response.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

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
        ApiResponse apiError = new ApiResponse(ResponseData.FAILURE, ex.getMessage());

        return new ResponseEntity<>(apiError, HttpStatus.OK);

    }

}
