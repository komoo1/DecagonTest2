package com.decagon.stock.service;

import com.decagon.stock.dto.request.TransactionDTO;
import com.decagon.stock.dto.response.ApiResponse;
import com.decagon.stock.dto.response.ResponseData;
import com.decagon.stock.dto.response.TransactionResponse;
import com.decagon.stock.repository.TransactionRepository;
import com.decagon.stock.repository.data.TransactionData;
import com.decagon.stock.repository.data.TransactionSearchData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import util.TestDataUtil;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.when;

/**
 * @author Victor.Komolafe
 */
@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    private TransactionRepository transactionRepository = Mockito.mock(TransactionRepository.class);
    private TransactionService transactionService;

    @Before
    public void init(){
        transactionService = new TransactionService(transactionRepository);
    }

    @Test
    public void testSearchUserTransactions(){

        UUID txUuid = UUID.randomUUID();
        UUID userUuid = UUID.randomUUID();
        TransactionSearchData data1 = TestDataUtil.createMockTransactionSearchData(txUuid, "AAPL");
        TransactionSearchData data2 = TestDataUtil.createMockTransactionSearchData(UUID.randomUUID(),"NFLX");

        TransactionDTO dto = new TransactionDTO();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dto.setStartDate("2020-05-12 22:00:00");
        dto.setEndDate(dateFormat.format(Date.from(Instant.now())));
        Pageable page = PageRequest.of(dto.getPageNumber(), dto.getPageSize());

        List<TransactionSearchData> dataList = Arrays.asList(data1, data2);
        Page<TransactionSearchData> dataListPage = new PageImpl<>(dataList, page, dataList.size());

        when(transactionRepository.findAllTransactions(any(UUID.class), any(Date.class), any(Date.class), any(Pageable.class)))
                .thenReturn(dataListPage);

        TransactionResponse response = transactionService.searchUserTransactions(dto, userUuid.toString());
        assertThat(response).extracting(ApiResponse::getCode).isEqualTo(ResponseData.SUCCESS);
        assertThat(response).extracting(TransactionResponse::getData).isNotNull();
    }

    @Test
    public void testCloseTransaction(){
        when(transactionRepository.updateTransaction(any(BigDecimal.class), anyDouble(), anyBoolean(), any(UUID.class)))
                .thenReturn(1);
        boolean status = transactionService.closeTransaction(UUID.randomUUID(), BigDecimal.TEN, new Random().nextDouble());
        assertTrue(status);
    }

    @Test
    public void testUpdateTransactionAmount(){
        when(transactionRepository.updateTransaction(any(BigDecimal.class), any(UUID.class)))
                .thenReturn(1);
          boolean status = transactionService.updateTransactionAmount(UUID.randomUUID(), BigDecimal.ONE);
        assertTrue(status);
    }

    @Test
    public void testGetTransactionByUuid(){
        UUID txUuid = UUID.randomUUID();
        UUID userUuid = UUID.randomUUID();
        TransactionData data = TestDataUtil.createMockTransactionData(txUuid, userUuid, "AAPL");

        when(transactionRepository.findFirstByUuid(any(UUID.class), any(UUID.class)))
                .thenReturn(Optional.of(data));

        Optional<TransactionData> transaction = transactionService.getTransactionByUuid(txUuid.toString(), userUuid.toString());
        assertTrue(transaction.isPresent());
        assertThat(transaction.get()).extracting(TransactionData::getUuid).isEqualTo(txUuid);
    }

}
