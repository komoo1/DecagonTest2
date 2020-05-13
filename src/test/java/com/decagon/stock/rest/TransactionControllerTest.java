package com.decagon.stock.rest;

import com.decagon.stock.dto.request.TransactionDTO;
import com.decagon.stock.dto.response.ResponseData;
import com.decagon.stock.dto.response.TransactionResponse;
import com.decagon.stock.repository.data.TransactionSearchData;
import com.decagon.stock.security.AuthDetailFactory;
import com.decagon.stock.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import util.TestDataUtil;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Victor.Komolafe
 */
@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest {

    private MockMvc mockMvc;
    private TransactionService transactionService = Mockito.mock(TransactionService.class);
    private AuthDetailFactory authDetailFactory = Mockito.mock(AuthDetailFactory.class);
    private TransactionController transactionController;
    private final ObjectMapper mapper = new ObjectMapper();
    private final UUID userUuid = UUID.randomUUID();

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        transactionController = new TransactionController(transactionService, authDetailFactory);
        mockMvc = MockMvcBuilders
                .standaloneSetup(transactionController)
                .build();
    }

    @Test
    public void searchUserTransactions() throws Exception {
        TransactionDTO dto = new TransactionDTO();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dto.setStartDate("2020-05-12 22:00:00");
        dto.setEndDate(dateFormat.format(Date.from(Instant.now())));
        dto.setPageSize(10);
        dto.setPageNumber(0);

        TransactionSearchData data1 = TestDataUtil.createMockTransactionSearchData(UUID.randomUUID(), "AAPL");
        TransactionSearchData data2 = TestDataUtil.createMockTransactionSearchData(UUID.randomUUID(),"NFLX");
        List<TransactionSearchData> dataList = Arrays.asList(data1, data2);

        TransactionResponse response = new TransactionResponse(dataList);
        response.setCode(ResponseData.SUCCESS); response.setDescription(ResponseData.SUCCESS_MESSAGE);

        when(transactionService.searchUserTransactions(dto, userUuid.toString()))
                .thenReturn(response);

         mockMvc.perform(post("/transaction/search")
                .header("authToken", "")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();
    }
}
