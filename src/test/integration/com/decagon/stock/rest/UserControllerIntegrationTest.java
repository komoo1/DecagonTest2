package com.decagon.stock.rest;

import com.decagon.stock.dto.request.UserDTO;
import com.decagon.stock.dto.response.ApiResponse;
import com.decagon.stock.dto.response.LoginResponse;
import com.decagon.stock.dto.response.ResponseData;
import com.decagon.stock.service.UserService;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
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

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Victor.Komolafe
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    UserService userService;

    @Before
    public void init(){
    }

    @Test
    @Sql("/db/sql/delete_users.sql")
    public void register(){
        String url = "/user/register";
        UserDTO dto = new UserDTO();
        dto.setUsername("testuser");
        dto.setPassword("pass");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);

        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(url, requestEntity, ApiResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).extracting(ApiResponse::getCode).isEqualTo(ResponseData.SUCCESS);
    }

    @Test
    @Sql({"/db/sql/delete_users.sql", "/db/sql/insert_users.sql"})
    public void login(){
        String url = "/user/login";

        UserDTO dto = new UserDTO();
        dto.setUsername("test");
        dto.setPassword("pass");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);

        ResponseEntity<LoginResponse> response = restTemplate.postForEntity(url, requestEntity, LoginResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        assertThat(response.getBody()).extracting(LoginResponse::getCode).isEqualTo(ResponseData.SUCCESS);
        assertThat(response.getBody()).extracting(LoginResponse::getToken).isNotNull();
    }

    @Test
    public void loginBadRequest(){
        String url = "/user/login";
        UserDTO dto = new UserDTO();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
