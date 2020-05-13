package com.decagon.stock.rest;

import com.decagon.stock.dto.request.UserDTO;
import com.decagon.stock.dto.response.ApiResponse;
import com.decagon.stock.dto.response.LoginResponse;
import com.decagon.stock.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

/**
 * @author Victor.Komolafe
 */
@RestController
@RequestMapping("user")
public class UserController {

    private UserService userService;

     public UserController(UserService userService){
        this.userService = userService;
    }

    @Async("asyncExec")
    @GetMapping(value="/exists/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ApiResponse> checkUsernameAvalaibility(@PathVariable(name="username") String username){
        ApiResponse response = userService.isUsernameExist(username);
        return CompletableFuture.completedFuture(response);
    }

    @Async("asyncExec")
    @PostMapping(value="/register", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ApiResponse> register(@RequestBody @Valid UserDTO user){
        ApiResponse response = userService.registerUser(user.getUsername(), user.getPassword());
        return CompletableFuture.completedFuture(response);
    }

    @Async("asyncExec")
    @PostMapping(value="/login", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<LoginResponse> login(@RequestBody @Valid UserDTO user) throws IllegalAccessException {
        LoginResponse response = userService.loginUser(user.getUsername(), user.getPassword());
        return CompletableFuture.completedFuture(response);
    }
}
