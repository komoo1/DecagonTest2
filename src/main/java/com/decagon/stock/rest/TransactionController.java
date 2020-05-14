package com.decagon.stock.rest;

import com.decagon.stock.dto.request.TransactionDTO;
import com.decagon.stock.dto.response.TransactionResponse;
import com.decagon.stock.security.AuthDetail;
import com.decagon.stock.security.AuthDetailProvider;
import com.decagon.stock.security.SecureAccess;
import com.decagon.stock.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

/**
 * @author Victor.Komolafe
 */
@RestController
@RequestMapping("transaction")
public class TransactionController {

    private TransactionService transactionService;
    private AuthDetailProvider authProvider;

    @Autowired
    public TransactionController(TransactionService transactionService, AuthDetailProvider authProvider){
        this.transactionService = transactionService;
        this.authProvider = authProvider;
    }

    @Async("asyncExec")
    @SecureAccess
    @PostMapping(value="/search", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<TransactionResponse> searchUserTransactions(@RequestHeader("authToken") String token,
                                                                         @RequestBody @Valid TransactionDTO transactionDTO){
        AuthDetail authDetail = authProvider.getAuthDetail(token);
        TransactionResponse response = transactionService.searchUserTransactions(transactionDTO, authDetail.getUserUuid());
        return CompletableFuture.completedFuture(response);
    }

}
