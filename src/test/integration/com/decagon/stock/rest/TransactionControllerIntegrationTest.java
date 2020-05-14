package com.decagon.stock.rest;

import com.decagon.stock.dto.request.TransactionDTO;
import com.decagon.stock.dto.request.UserDTO;
import com.decagon.stock.dto.response.LoginResponse;
import com.decagon.stock.dto.response.ResponseData;
import com.decagon.stock.dto.response.TransactionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Victor.Komolafe
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionControllerIntegrationTest {

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
    @Sql(scripts = {"/db/sql/delete_users.sql",
        "/db/sql/insert_users.sql",
        "/db/sql/insert_transactions.sql"
    })
    public void searchUserTransactions(){
        String url = "/transaction/search";
        TransactionDTO dto = new TransactionDTO();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dto.setStartDate("2020-05-12 22:00:00");
        dto.setEndDate(dateFormat.format(Date.from(Instant.now())));

        HttpHeaders headers = new HttpHeaders();
        headers.add("authToken", REQUEST_TOKEN);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TransactionDTO> requestEntity = new HttpEntity<>(dto, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        System.out.println(response.getBody());

        //assertThat(response.getBody()).extracting(TransactionResponse::getCode).isEqualTo(ResponseData.SUCCESS);
    }

    @Test
    public void searchUserTransactionsNotAuthenticated(){
        String url = "/transaction/search";
        TransactionDTO dto = new TransactionDTO();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dto.setStartDate("2020-05-12 22:00:00");
        dto.setEndDate(dateFormat.format(Date.from(Instant.now())));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TransactionDTO> requestEntity = new HttpEntity<>(dto, headers);

        ResponseEntity<TransactionResponse> response = restTemplate.postForEntity(url, requestEntity, TransactionResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).extracting(TransactionResponse::getCode).isEqualTo(ResponseData.FAILURE);
    }
}
