package com.decagon.stock.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Victor.Komolafe
 */

@Getter
@AllArgsConstructor
public class LoginResponse extends ApiResponse {

    private String token;
}
