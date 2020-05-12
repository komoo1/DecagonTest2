package com.decagon.stock.rest;

import com.decagon.stock.dto.request.StockDTO;
import com.decagon.stock.service.StockService;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Victor.Komolafe
 */
@RestController
@RequestMapping("stock")
public class StockController {

    private StockService stockService;

    public StockController(StockService stockService){
        this.stockService = stockService;
    }

    @Async("asyncExec")
    @PostMapping(value="/buy", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void buy(@RequestBody StockDTO stockDTO){

    }

    @Async("asyncExec")
    @PostMapping(value="/sell", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void sell(@RequestBody StockDTO stockDTO){

    }

    @Async("asyncExec")
    @GetMapping(value="/priceLookUp", produces = MediaType.APPLICATION_JSON_VALUE)
    public void priceLookup(@RequestParam String stock){

    }
}
