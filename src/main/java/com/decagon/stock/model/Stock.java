package com.decagon.stock.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author Victor.Komolafe
 */

@Entity
@Getter
@Setter
public class Stock extends _Base {

    @Column(unique = true)
    private String symbol;
}
