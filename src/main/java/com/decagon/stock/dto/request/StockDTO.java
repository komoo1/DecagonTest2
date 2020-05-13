package com.decagon.stock.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Victor.Komolafe
 */
@Getter
@Setter
@NoArgsConstructor
public class StockDTO implements Serializable {
    @NotNull
    @NotBlank
    private String symbol;
    @NotNull
    @Min(value = 0)
    private BigDecimal amount;
    private String transactionUuid;
}
