package com.decagon.stock.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Victor.Komolafe
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse implements Serializable {
    private Integer code;
    private String description;
}
