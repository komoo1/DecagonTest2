package com.decagon.stock.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Victor.Komolafe
 */
@Getter
@Setter
@NoArgsConstructor
public class StockDTO implements Serializable {
    private String stockName;
    private BigDecimal amount;
    private Double price;
    private Double slippage;
}
