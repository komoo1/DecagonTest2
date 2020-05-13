package com.decagon.stock.service;


import com.decagon.stock.dto.response.ApiResponse;
import com.decagon.stock.dto.response.ResponseData;
import com.decagon.stock.model.Stock;
import com.decagon.stock.model.User;
import com.decagon.stock.repository.StockRepository;
import com.decagon.stock.repository.data.TransactionData;
import com.decagon.stock.service.iex.IEXService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import util.TestDataUtil;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

/**
 * @author Victor.Komolafe
 */
@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
@TestPropertySource("classpath:application.properties")
public class StockServiceTest {

    public static final String SYMBOL_NFLX = "NFLX";
    private StockRepository stockRepository = Mockito.mock(StockRepository.class);
    private TransactionService transactionService = Mockito.mock(TransactionService.class);
    private UserService userService = Mockito.mock(UserService.class);
    private IEXService iexService = Mockito.mock(IEXService.class);

    private StockService stockService;

    @Before
    public void init(){
        stockService = new StockService(stockRepository, transactionService, userService, iexService);
    }

    @Test
    public void testBuyStock(){
        Stock stock = new Stock();
        stock.setSymbol(SYMBOL_NFLX);

        User user1 = new User();
        user1.setUsername("test");
        UUID uuid = UUID.randomUUID();
        user1.setUuid(uuid);

        when(stockRepository.findFirstBySymbolIgnoreCase(anyString())).thenReturn(Optional.of(stock));
        when(userService.getUserByUuid(any(String.class))).thenReturn(Optional.of(user1));

        ApiResponse response = stockService.buyStock(SYMBOL_NFLX, BigDecimal.ONE, uuid.toString());
        assertThat(response).extracting(ApiResponse::getCode).isEqualTo(ResponseData.SUCCESS);
    }

    @Test
    public void testSellStockPartial(){
        UUID txUuid = UUID.randomUUID();
        UUID userUuid = UUID.randomUUID();
        TransactionData data = TestDataUtil.createMockTransactionData(txUuid, userUuid, SYMBOL_NFLX);

        Stock stock = new Stock();
        stock.setSymbol(SYMBOL_NFLX);

        User user = new User();
        user.setUuid(userUuid);

        when(transactionService.getTransactionByUuid(txUuid.toString(), userUuid.toString())).thenReturn(Optional.of(data));
        when(stockRepository.findFirstBySymbolIgnoreCase(anyString())).thenReturn(Optional.of(stock));
        when(userService.getUserByUuid(anyString())).thenReturn(Optional.of(user));

        ApiResponse response = stockService.sellStock(SYMBOL_NFLX, txUuid.toString(), BigDecimal.ONE, userUuid.toString());
        assertThat(response).extracting(ApiResponse::getCode).isEqualTo(ResponseData.SUCCESS);
    }

    @Test
    public void testSellStockSellAll(){
        UUID txUuid = UUID.randomUUID();
        UUID userUuid = UUID.randomUUID();
        TransactionData data = TestDataUtil.createMockTransactionData(txUuid, userUuid, SYMBOL_NFLX);

        Stock stock = new Stock();
        stock.setSymbol(SYMBOL_NFLX);

        User user = new User();
        user.setUuid(userUuid);

        when(transactionService.getTransactionByUuid(txUuid.toString(), userUuid.toString())).thenReturn(Optional.of(data));
        when(stockRepository.findFirstBySymbolIgnoreCase(anyString())).thenReturn(Optional.of(stock));
        when(userService.getUserByUuid(anyString())).thenReturn(Optional.of(user));

        ApiResponse response = stockService.sellStock(SYMBOL_NFLX, txUuid.toString(), BigDecimal.TEN, userUuid.toString());
        assertThat(response).extracting(ApiResponse::getCode).isEqualTo(ResponseData.SUCCESS);
    }

    @Test
    public void testSellStockInvalidTransaction(){
        UUID txUuid = UUID.randomUUID();
        UUID userUuid = UUID.randomUUID();

        when(transactionService.getTransactionByUuid(txUuid.toString(), userUuid.toString())).thenReturn(Optional.ofNullable(null));

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            stockService.sellStock(SYMBOL_NFLX, txUuid.toString(), BigDecimal.ONE, userUuid.toString());
        }).withMessage(ResponseData.INVALID_DATA_MESSAGE);
    }

    @Test
    public void testSellStockInvalidSymbol(){
        UUID txUuid = UUID.randomUUID();
        UUID userUuid = UUID.randomUUID();
        TransactionData data = TestDataUtil.createMockTransactionData(txUuid, userUuid, SYMBOL_NFLX);

        when(transactionService.getTransactionByUuid(txUuid.toString(), userUuid.toString())).thenReturn(Optional.ofNullable(data));

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            stockService.sellStock("AAPL", txUuid.toString(), BigDecimal.ONE, userUuid.toString());
        }).withMessage(ResponseData.INVALID_DATA_MESSAGE);
    }

    @Test
    public void testLookupStock(){
        ApiResponse response = stockService.lookupStockPrice(SYMBOL_NFLX);
        assertThat(response).extracting(ApiResponse::getCode).isEqualTo(ResponseData.SUCCESS);
    }



}
