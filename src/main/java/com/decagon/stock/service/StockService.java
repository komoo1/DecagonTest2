package com.decagon.stock.service;

import com.decagon.stock.repository.StockRepository;
import org.springframework.stereotype.Service;

/**
 * @author Victor.Komolafe
 */
@Service
public class StockService {

    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository){
        this.stockRepository = stockRepository;
    }
}
