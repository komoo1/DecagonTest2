package com.decagon.stock.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * @author Victor.Komolafe
 */

@Getter
public class ApiError extends ApiResponse {

    private HttpStatus status;
    private List<String> errors;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private String timestamp;

    public ApiError(HttpStatus status, String description, List<String> errors) {
        this.status = status;
        this.errors = errors;

        setCode(ResponseData.FAILURE);
        setDescription(description);
        this.timestamp = ZonedDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public ApiError(HttpStatus status, String description, String error) {
        this(status, description, Arrays.asList(error));
    }
}