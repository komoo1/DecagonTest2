package com.decagon.stock.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Victor.Komolafe
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse extends ApiResponse {

    private String token;
}
