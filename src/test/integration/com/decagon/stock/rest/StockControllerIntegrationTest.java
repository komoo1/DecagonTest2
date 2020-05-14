package com.decagon.stock.rest;

import com.decagon.stock.dto.request.StockDTO;
import com.decagon.stock.dto.request.UserDTO;
import com.decagon.stock.dto.response.ApiResponse;
import com.decagon.stock.dto.response.LoginResponse;
import com.decagon.stock.dto.response.ResponseData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Victor.Komolafe
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StockControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;
    private String REQUEST_TOKEN;

    @Before
    public void init(){
        String url = "/user/login";
        UserDTO dto = new UserDTO();
        dto.setUsername("test");
        dto.setPassword("pass");

        LoginResponse response = restTemplate.postForObject(url, dto, LoginResponse.class);
        REQUEST_TOKEN = response.getToken();
    }

    @Test
    @SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts ={"/db/sql/insert_users.sql"}),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"/db/sql/delete_transactions.sql", "/db/sql/delete_users.sql"}),
    })
    public void buyStock(){
        String url = "/stock/buy";
        StockDTO dto = new StockDTO();
        dto.setSymbol("NFLX");
        dto.setAmount(BigDecimal.TEN);

        HttpHeaders headers = new HttpHeaders();
        headers.add("authToken", REQUEST_TOKEN);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<StockDTO> requestEntity = new HttpEntity<>(dto, headers);

        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(url, requestEntity, ApiResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).extracting(ApiResponse::getCode).isEqualTo(ResponseData.SUCCESS);
    }

    @Test
    public void buyStockNotAuthenticated(){
        String url = "/stock/buy";
        StockDTO dto = new StockDTO();
        dto.setSymbol("NFLX");
        dto.setAmount(BigDecimal.TEN);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<StockDTO> requestEntity = new HttpEntity<>(dto, headers);

        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(url, requestEntity, ApiResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).extracting(ApiResponse::getCode).isEqualTo(ResponseData.FAILURE);
    }

    @Test
    @SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts ={"/db/sql/insert_users.sql", "/db/sql/insert_transactions.sql"}),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"/db/sql/delete_transactions.sql", "/db/sql/delete_users.sql"}),
    })
    public void sellStockInvalidTransactionUuuid(){
        String url = "/stock/sell";
        StockDTO dto = new StockDTO();
        dto.setSymbol("NFLX");
        dto.setAmount(BigDecimal.TEN);
        dto.setTransactionUuid("6b48248d-6ff2-4b4d-a533-e17ec5e3d93d");

        HttpHeaders headers = new HttpHeaders();
        headers.add("authToken", REQUEST_TOKEN);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<StockDTO> requestEntity = new HttpEntity<>(dto, headers);

        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(url, requestEntity, ApiResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).extracting(ApiResponse::getCode).isEqualTo(ResponseData.FAILURE);
    }

    @Test
    public void sellStockNotAuthenticated(){
        String url = "/stock/sell";
        StockDTO dto = new StockDTO();
        dto.setSymbol("NFLX");
        dto.setAmount(BigDecimal.TEN);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<StockDTO> requestEntity = new HttpEntity<>(dto, headers);

        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(url, requestEntity, ApiResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).extracting(ApiResponse::getCode).isEqualTo(ResponseData.FAILURE);
    }

}
