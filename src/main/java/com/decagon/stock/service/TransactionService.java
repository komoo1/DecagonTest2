package com.decagon.stock.service;

import com.decagon.stock.dto.request.TransactionDTO;
import com.decagon.stock.repository.TransactionRepository;
import org.springframework.stereotype.Service;

/**
 * @author Victor.Komolafe
 */
@Service
public class TransactionService {

    private TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }

    public void searchUserTransactions(TransactionDTO transactionDTO){

    }
}
