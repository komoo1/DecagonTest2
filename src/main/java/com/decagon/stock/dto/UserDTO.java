package com.decagon.stock.dto;

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
public class UserDTO implements Serializable {
    private String username;
    private String password;
}
