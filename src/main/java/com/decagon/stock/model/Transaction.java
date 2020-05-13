package com.decagon.stock.model;

import com.decagon.stock.repository.data.TransactionData;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Victor.Komolafe
 */

@Entity
@Getter
@Setter
public class Transaction extends _Base {

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Column
    private BigDecimal amount;

    @Column(name = "open_price", updatable = false)
    private Double openPrice;

    @Column(name = "close_price")
    private Double closePrice;

    @Column
    private boolean active;

    @Column
    private UUID uuid = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Stock stock;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private User user;
}
