package com.decagon.stock.service;

import com.decagon.stock.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author Victor.Komolafe
 */
@Service
public class StockService {

    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository){
        this.stockRepository = stockRepository;
    }

    public String buyStock(String stockName, BigDecimal amount, Double price){

        return null;
    }

    public String sellStock(String stockName, BigDecimal amount){
        return null;
    }

    public String lookupStockPrice(String stockName){
        return null;
    }
}
