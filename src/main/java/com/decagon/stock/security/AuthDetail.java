/*
 * All rights reserved. VANSO 2016
 */
package com.decagon.stock.security;

import com.decagon.stock.repository.data.UserData;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 *
 * @author Victor.Komolafe
 */
@Getter
@Setter
@RequiredArgsConstructor
public class AuthDetail implements Serializable {

    private final String userUuid;
    private Boolean active;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private String timestamp;
}