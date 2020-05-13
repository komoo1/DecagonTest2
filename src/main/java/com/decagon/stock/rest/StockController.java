package com.decagon.stock.rest;

import com.decagon.stock.dto.request.StockDTO;
import com.decagon.stock.dto.response.ApiResponse;
import com.decagon.stock.security.AuthDetail;
import com.decagon.stock.security.AuthDetailFactory;
import com.decagon.stock.security.AuthDetailFactoryHelper;
import com.decagon.stock.security.SecureAccess;
import com.decagon.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

/**
 * @author Victor.Komolafe
 */
@RestController
@RequestMapping("stock")
public class StockController {

    private StockService stockService;
    private AuthDetailFactory authDetailFactory;

    @Autowired
    public StockController(StockService stockService, AuthDetailFactory authDetailFactory){
        this.stockService = stockService;
        this.authDetailFactory = authDetailFactory;
    }

    @Async("asyncExec")
    @SecureAccess
    @PostMapping(value="/buy", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ApiResponse> buy(@RequestBody @Valid StockDTO stockDTO){
        AuthDetail authDetail = new AuthDetailFactoryHelper(authDetailFactory).getAuthDetail();
        ApiResponse response = stockService.buyStock(stockDTO.getSymbol(), stockDTO.getAmount(), authDetail.getUserUuid());
        return CompletableFuture.completedFuture(response);
    }

    @Async("asyncExec")
    @SecureAccess
    @PostMapping(value="/sell", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ApiResponse> sell(@RequestBody @Valid StockDTO stockDTO){
        AuthDetail authDetail = authDetailFactory.getAuthDetail();
        ApiResponse response = stockService.sellStock(stockDTO.getSymbol(), stockDTO.getTransactionUuid(), stockDTO.getAmount(), authDetail.getUserUuid());
        return CompletableFuture.completedFuture(response);
    }

    @Async("asyncExec")
    @SecureAccess
    @GetMapping(value="/pricelookup", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ApiResponse> priceLookup(@RequestParam String symbol){
        ApiResponse response = stockService.lookupStockPrice(symbol);
        return CompletableFuture.completedFuture(response);
    }
}
