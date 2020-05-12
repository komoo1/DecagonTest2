package com.decagon.stock.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Victor.Komolafe
 */
@Getter
@Setter
@NoArgsConstructor
public class UserDTO implements Serializable {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
