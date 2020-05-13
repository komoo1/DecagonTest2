package com.decagon.stock.rest;

import com.decagon.stock.dto.request.UserDTO;
import com.decagon.stock.dto.response.ApiResponse;
import com.decagon.stock.dto.response.LoginResponse;
import com.decagon.stock.dto.response.ResponseData;
import com.decagon.stock.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Victor.Komolafe
 */
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    private MockMvc mockMvc;
    private UserService userService = Mockito.mock(UserService.class);
    private UserController userController;
    private final ObjectMapper mapper = new ObjectMapper();

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        userController = new UserController(userService);
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .build();
    }

    @Test
    public void login() throws Exception {
        UserDTO dto = new UserDTO();
        dto.setUsername("user");
        dto.setPassword("pass");
        LoginResponse response = new LoginResponse(UUID.randomUUID().toString());
        response.setCode(ResponseData.SUCCESS);

        when(userService.loginUser(dto.getUsername(), dto.getPassword())).thenReturn(response);

        MvcResult mvcResult = mockMvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();

        assertThat((LoginResponse)mvcResult.getAsyncResult()).extracting(LoginResponse::getCode).isEqualTo(ResponseData.SUCCESS);

    }

    @Test
    public void register() throws Exception {
        UserDTO dto = new UserDTO();
        dto.setUsername("user");
        dto.setPassword("pass");

        ApiResponse response = new ApiResponse(ResponseData.SUCCESS, ResponseData.SUCCESS_MESSAGE);

        when(userService.registerUser(dto.getUsername(), dto.getPassword())).thenReturn(response);

        MvcResult mvcResult = mockMvc.perform(post("/user/register").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn();

        assertThat((ApiResponse)mvcResult.getAsyncResult()).extracting(ApiResponse::getCode).isEqualTo(ResponseData.SUCCESS);
    }

    @Test
    public void usernameAvailability() throws Exception {
        ApiResponse response = new ApiResponse(ResponseData.DATA_NOT_FOUND, ResponseData.USER_VALIDATION_ERROR_MESSAGE);
        when(userService.isUsernameExist(anyString())).thenReturn(response);

        MvcResult mvcResult = mockMvc.perform(get("/user/exists/{username}", "unknown"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat((ApiResponse)mvcResult.getAsyncResult()).extracting(ApiResponse::getCode).isEqualTo(ResponseData.DATA_NOT_FOUND);

    }
}
