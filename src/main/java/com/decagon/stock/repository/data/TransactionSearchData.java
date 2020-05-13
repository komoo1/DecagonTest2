package com.decagon.stock.repository.data;

import com.decagon.stock.model.TransactionType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * @author Victor.Komolafe
 */
public interface TransactionSearchData extends Serializable {

    TransactionType getType();

    BigDecimal getAmount();

    Double getOpenPrice();

    Double getClosePrice();

    Date getCreatedDate();

     boolean isActive();

    UUID getUuid();

    StockData getStock();
}
