package com.decagon.stock.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Victor.Komolafe
 */
@Getter
@Setter
public class ApiResponse implements Serializable {
    private Integer code;
    private String description;
}
