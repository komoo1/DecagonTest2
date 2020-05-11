package com.decagon.stock.repository.data;

import com.decagon.stock.model.TransactionType;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Victor.Komolafe
 */
public interface TransactionData {

    TransactionType getType();

    BigDecimal getAmount();

    Double getOpenPrice();

    Double getClosePrice();

     boolean isActive();

    UUID getUuid();

    UserData getUser();

    StockData getStock();
}
