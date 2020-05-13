package com.decagon.stock.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.UUID;

/**
 * @author Victor.Komolafe
 */
@Entity
@Getter
@Setter
public class User extends _Base {

    @Column(unique = true)
    private String username;

    @Column
    private UUID uuid = UUID.randomUUID();

    @Column(nullable = false)
    private String passwordHash;
}
