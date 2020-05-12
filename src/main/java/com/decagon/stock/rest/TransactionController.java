package com.decagon.stock.rest;

import com.decagon.stock.dto.TransactionDTO;
import com.decagon.stock.service.TransactionService;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Victor.Komolafe
 */
@RestController
@RequestMapping("transaction")
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @Async("asyncExec")
    @PostMapping(value="/search", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void searchUserTransactions(@RequestBody TransactionDTO transactionDTO){

    }

}
