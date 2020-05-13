package com.decagon.stock.service;

import com.decagon.stock.dto.response.ApiResponse;
import com.decagon.stock.dto.response.LoginResponse;
import com.decagon.stock.dto.response.ResponseData;
import com.decagon.stock.model.User;
import com.decagon.stock.repository.UserRepository;
import com.decagon.stock.security.AuthDetailProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

/**
 * @author Victor.Komolafe
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private UserRepository userRepository = Mockito.mock(UserRepository.class);
    private AuthDetailProvider authProvider = Mockito.mock(AuthDetailProvider.class);

    private UserService userService;

    @Before
    public void init() {
        userService = new UserService(userRepository, authProvider);
    }

    @Test
    public void testUsernameExist(){
        User user1 = new User();
        user1.setUsername("test");
        user1.setPasswordHash(BCrypt.hashpw("pass", BCrypt.gensalt()));
        when(userRepository.findFirstByUsernameIgnoreCase(any(String.class))).thenReturn(Optional.of(user1));

        ApiResponse response = userService.isUsernameExist("test");
        assertThat(response).extracting(ApiResponse::getCode).isEqualTo(ResponseData.SUCCESS);
    }

    @Test
    public void testUsernameNotExist(){
        when(userRepository.findFirstByUsernameIgnoreCase(any(String.class))).thenReturn(Optional.ofNullable(null));

        ApiResponse response = userService.isUsernameExist("unknown");
        assertThat(response).extracting(ApiResponse::getCode).isEqualTo(ResponseData.DATA_NOT_FOUND);
    }

    @Test
    public void testRegisterUser() {
        ApiResponse response = userService.registerUser("test", "pass");
        assertThat(response).extracting(ApiResponse::getCode).isEqualTo(ResponseData.SUCCESS);
    }

    @Test
    public void testLoginUserValid() throws Exception {
        User user1 = new User();
        user1.setUsername("test");
        user1.setPasswordHash(BCrypt.hashpw("pass", BCrypt.gensalt()));
        when(userRepository.findFirstByUsernameIgnoreCase(any(String.class))).thenReturn(Optional.of(user1));

        LoginResponse response = userService.loginUser("test", "pass");
        assertThat(response).extracting(ApiResponse::getCode).isEqualTo(ResponseData.SUCCESS);
    }

   @Test
    public void testLoginUserNotValidUsername() {
        when(userRepository.findFirstByUsernameIgnoreCase(any(String.class))).thenReturn(Optional.ofNullable(null));

       assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
           userService.loginUser("wrongtest", "pass");
       }).withMessage(ResponseData.USER_VALIDATION_ERROR_MESSAGE);

    }

    @Test
    public void testLoginUserNotValidPassword() {
        User user1 = new User();
        user1.setUsername("test");
        user1.setPasswordHash(BCrypt.hashpw("pass", BCrypt.gensalt()));
        when(userRepository.findFirstByUsernameIgnoreCase(any(String.class))).thenReturn(Optional.of(user1));

        assertThatExceptionOfType(IllegalAccessException.class).isThrownBy(() -> {
            userService.loginUser("test", "wrongpass");
         }).withMessage(ResponseData.USER_VALIDATION_ERROR_MESSAGE);
    }

    @Test
    public void testGetUserByUuid() {
        User user1 = new User();
        user1.setUsername("test");
        user1.setPasswordHash(BCrypt.hashpw("pass", BCrypt.gensalt()));
        UUID uuid = UUID.randomUUID();
        user1.setUuid(uuid);

        when(userRepository.findFirstByUuid(any(UUID.class))).thenReturn(Optional.of(user1));

        Optional<User> userByUuid = userService.getUserByUuid(uuid.toString());
        assertTrue(userByUuid.isPresent());
        assertThat(userByUuid.get()).extracting(User::getUsername).isEqualTo(user1.getUsername());
    }
}
