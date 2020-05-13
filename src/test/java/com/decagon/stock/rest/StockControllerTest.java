package com.decagon.stock.rest;

import com.decagon.stock.dto.request.StockDTO;
import com.decagon.stock.dto.response.ApiResponse;
import com.decagon.stock.dto.response.ResponseData;
import com.decagon.stock.security.AuthDetail;
import com.decagon.stock.security.AuthDetailFactory;
import com.decagon.stock.security.AuthDetailFactoryHelper;
import com.decagon.stock.service.StockService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.PowerMockUtils;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Victor.Komolafe
 */
@RunWith(MockitoJUnitRunner.class)
public class StockControllerTest {

    private MockMvc mockMvc;

    private StockService stockService = Mockito.mock(StockService.class);
    private AuthDetailFactory authDetailFactory = Mockito.mock(AuthDetailFactory.class);
    private AuthDetailFactoryHelper authDetailFactoryHelper;

    private StockController stockController;
    private final ObjectMapper mapper = new ObjectMapper();
    private final UUID userUuid = UUID.randomUUID();

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        stockController = new StockController(stockService, authDetailFactory);
        authDetailFactoryHelper = new AuthDetailFactoryHelper(authDetailFactory);

        mockMvc = MockMvcBuilders
                .standaloneSetup(stockController)
                .build();
    }

    @Test
    public void buy() throws Exception {
        StockDTO dto = new StockDTO();
        dto.setSymbol("NFLX");
        dto.setAmount(BigDecimal.TEN);
        ApiResponse response = new ApiResponse(ResponseData.SUCCESS, ResponseData.SUCCESS_MESSAGE);

        when(new AuthDetailFactoryHelper(authDetailFactory).getAuthDetail()).thenReturn(new AuthDetail(UUID.randomUUID().toString()));
        when(stockService.buyStock(dto.getSymbol(), dto.getAmount(), userUuid.toString()))
                .thenReturn(response);

        mockMvc.perform(post("/stock/buy")
                .header("authToken", "234353")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void sell() throws Exception {
        StockDTO dto = new StockDTO();
        dto.setSymbol("NFLX");
        dto.setAmount(BigDecimal.ONE);
        dto.setTransactionUuid(UUID.randomUUID().toString());
        ApiResponse response = new ApiResponse(ResponseData.SUCCESS, ResponseData.SUCCESS_MESSAGE);

        when(stockService.sellStock(dto.getSymbol(), dto.getTransactionUuid(),dto.getAmount(), userUuid.toString()))
                .thenReturn(response);

         mockMvc.perform(post("/stock/sell")
                .header("authToken", "")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void priceLookup() throws Exception {
        ApiResponse response = new ApiResponse(ResponseData.SUCCESS, BigDecimal.valueOf(7232.225).toPlainString());

        when(stockService.lookupStockPrice(anyString()))
                .thenReturn(response);

       mockMvc.perform(get("/stock/pricelookup?symbol=NFLX")
                .header("authToken", ""))
                .andExpect(status().isOk())
                .andReturn();


    }
}
