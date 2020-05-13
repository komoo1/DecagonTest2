package com.decagon.stock.service;

import com.decagon.stock.dto.request.TransactionDTO;
import com.decagon.stock.dto.response.ResponseData;
import com.decagon.stock.dto.response.TransactionResponse;
import com.decagon.stock.model.Stock;
import com.decagon.stock.model.Transaction;
import com.decagon.stock.model.TransactionType;
import com.decagon.stock.model.User;
import com.decagon.stock.repository.TransactionRepository;
import com.decagon.stock.repository.data.TransactionData;
import com.decagon.stock.repository.data.TransactionSearchData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Victor.Komolafe
 */
@Service
public class TransactionService {

    private TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }

    @Transactional(readOnly = true)
    public TransactionResponse searchUserTransactions(TransactionDTO transactionDTO, String userUuid) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Page<TransactionSearchData> transactionList = Page.empty();

        try {
            UUID useruid = UUID.fromString(userUuid);
            Date start = dateFormat.parse(transactionDTO.getStartDate());
            Date end = dateFormat.parse(transactionDTO.getEndDate());
            Pageable page = PageRequest.of(transactionDTO.getPageNumber(), transactionDTO.getPageSize());

            transactionList = transactionRepository.findAllTransactions(useruid, start, end, page);

        }catch (ParseException ex){
            throw new IllegalArgumentException(ResponseData.INVALID_DATA_MESSAGE);
        }

        TransactionResponse transactionResponse = new TransactionResponse(transactionList.getContent());
        transactionResponse.setCode(ResponseData.SUCCESS);
        transactionResponse.setDescription(ResponseData.SUCCESS_MESSAGE);

        return transactionResponse;
    }

    public void createTransaction(Stock stock, User user, TransactionType type, BigDecimal amount,
                                  Double openPrice, Double closePrice, Boolean active){
        Transaction transaction = new Transaction();
        transaction.setStock(stock);
        transaction.setUser(user);
        transaction.setType(type);
        transaction.setActive(active);
        transaction.setAmount(amount);
        transaction.setOpenPrice(openPrice);
        transaction.setClosePrice(closePrice);

        transactionRepository.save(transaction);
    }

    public boolean closeTransaction(UUID transactionUuid, BigDecimal amount, Double closePrice){

        int result = transactionRepository.updateTransaction(amount, closePrice, false, transactionUuid);
        if(result < 1) {
            return false;
        }

        return true;
    }

    public boolean updateTransactionAmount(UUID transactionUuid, BigDecimal amount){

        int result = transactionRepository.updateTransaction(amount, transactionUuid);
        if(result < 1) {
            return false;
        }

        return true;
    }



    public Optional<TransactionData> getTransactionByUuid(String uuidString, String userUuid){
        UUID uuid = UUID.fromString(uuidString);
        UUID userUid = UUID.fromString(userUuid);

        return transactionRepository.findFirstByUuid(uuid, userUid);
    }
}
