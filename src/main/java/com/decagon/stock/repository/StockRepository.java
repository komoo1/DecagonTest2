package com.decagon.stock.repository;

import com.decagon.stock.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Victor.Komolafe
 */
@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    /**
     * Retrieve a stock by symbol
     * @param symbol
     * @return
     */
    Optional<Stock> findFirstBySymbolIgnoreCase(String symbol);
}
