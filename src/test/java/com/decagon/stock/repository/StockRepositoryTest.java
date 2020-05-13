package com.decagon.stock.repository;

import com.decagon.stock.model.Stock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Victor.Komolafe
 */
@RunWith(SpringRunner.class)
@DataJpaTest()
@TestPropertySource("classpath:application.properties")
public class StockRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StockRepository stockRepository;

    @Before
    public void init(){
        Stock stock = new Stock();
        stock.setSymbol("NFLX");

        Stock stock2 = new Stock();
        stock.setSymbol("AAPL");

        entityManager.persist(stock);
        entityManager.persist(stock2);
    }

    @Test
    public void testFindFirstBySymbolIgnoreCaseFound(){
        Optional<Stock> stock = stockRepository.findFirstBySymbolIgnoreCase("NFLX");
        assertTrue(stock.isPresent());
        assertThat(stock.get()).extracting(Stock::getSymbol).isEqualTo("NFLX");
    }

    @Test
    public void testFindFirstBySymbolIgnoreCaseNotFound(){
        Optional<Stock> stock = stockRepository.findFirstBySymbolIgnoreCase("Unknown");
        assertFalse(stock.isPresent());
    }

}
