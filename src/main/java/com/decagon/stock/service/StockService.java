package com.decagon.stock.service;

import com.decagon.stock.dto.response.ApiResponse;
import com.decagon.stock.dto.response.ResponseData;
import com.decagon.stock.model.Stock;
import com.decagon.stock.model.TransactionType;
import com.decagon.stock.model.User;
import com.decagon.stock.repository.StockRepository;
import com.decagon.stock.repository.data.TransactionData;
import com.decagon.stock.service.iex.IEXService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author Victor.Komolafe
 */
@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final TransactionService transactionService;
    private final UserService userService;
    private final IEXService iexService;

    public ApiResponse buyStock(String symbol, BigDecimal amount, String userUuid){

        Optional<Stock> stock = stockRepository.findFirstBySymbolIgnoreCase(symbol);
        Optional<User> user = userService.getUserByUuid(userUuid);

        if(!stock.isPresent() || !user.isPresent()){
            throw new IllegalArgumentException(ResponseData.INVALID_DATA_MESSAGE);
        }

        Double iexStockPrice = iexService.getIEXStockPrice(symbol);

       transactionService.createTransaction(stock.get(), user.get(),
                TransactionType.BUY, amount, iexStockPrice, null, true);

        return new ApiResponse(ResponseData.SUCCESS, ResponseData.SUCCESS_MESSAGE);
    }

    @Transactional
    public ApiResponse sellStock(String symbol, String transactionUuid, BigDecimal amount, String userUuid){

        Optional<TransactionData> transactionData = transactionService.getTransactionByUuid(transactionUuid, userUuid);
        if(!transactionData.isPresent()){
            throw new IllegalArgumentException(ResponseData.INVALID_DATA_MESSAGE);
        }

        TransactionData transaction = transactionData.get();
        if(TransactionType.SELL.equals(transaction.getType()) ||
                amount.compareTo(transaction.getAmount()) > 0 ||
                !symbol.equalsIgnoreCase(transaction.getStock().getSymbol()) ||
                !transaction.isActive())
        {
            throw new IllegalArgumentException(ResponseData.INVALID_DATA_MESSAGE);
        }

        Optional<Stock> stock = stockRepository.findFirstBySymbolIgnoreCase(transaction.getStock().getSymbol());
        Optional<User> user = userService.getUserByUuid(transaction.getUser().getUuid().toString());

        boolean isSellAll = amount.compareTo(transaction.getAmount()) == 0;
        Double iexStockPrice = iexService.getIEXStockPrice(transaction.getStock().getSymbol());

         if(isSellAll) { // exact amount
            transactionService.closeTransaction(transaction.getUuid(), transaction.getAmount(), iexStockPrice);
        }
        else {
            transactionService.updateTransactionAmount(transaction.getUuid(), transaction.getAmount().subtract(amount));
            transactionService.createTransaction(stock.get(), user.get(),
                    TransactionType.SELL, amount, transaction.getOpenPrice(), iexStockPrice, false);
        }

        return new ApiResponse(ResponseData.SUCCESS, ResponseData.SUCCESS_MESSAGE);
    }

    public ApiResponse lookupStockPrice(String symbol){
        Double price = iexService.getIEXStockPrice(symbol);
        return new ApiResponse(ResponseData.SUCCESS, String.valueOf(price));
    }

}
