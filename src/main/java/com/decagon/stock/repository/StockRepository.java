package com.decagon.stock.repository;

import com.decagon.stock.model.Stock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Victor.Komolafe
 */
@Repository
public interface StockRepository extends _BaseRepository<Stock, Long> {

    /**
     * Retrieve a stock by name
     * @param name
     * @return
     */
    Optional<Stock> findFirstByNameIgnoreCase(String name);
}
