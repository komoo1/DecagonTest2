package com.decagon.stock.service;

import com.decagon.stock.dto.response.ApiResponse;
import com.decagon.stock.dto.response.LoginResponse;
import com.decagon.stock.dto.response.ResponseData;
import com.decagon.stock.model.User;
import com.decagon.stock.repository.UserRepository;
import com.decagon.stock.security.AuthDetail;
import com.decagon.stock.security.AuthDetailProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Victor.Komolafe
 */
@Service
public class UserService {

    private UserRepository userRepository;
    private AuthDetailProvider authProvider;

    @Autowired
    public UserService(UserRepository userRepository, AuthDetailProvider authProvider){
        this.userRepository = userRepository;
        this.authProvider = authProvider;
    }

    public ApiResponse isUsernameExist(String username){
        Optional<User> userObj = userRepository.findFirstByUsernameIgnoreCase(username);

        if(!userObj.isPresent()){
            return new ApiResponse(ResponseData.DATA_NOT_FOUND, ResponseData.USER_NOT_EXITS_MESSAGE);
        }

        return new ApiResponse(ResponseData.SUCCESS, ResponseData.USER_EXISTS_MESSAGE);
    }

    public ApiResponse registerUser(String username, String password){

        User user = new User();
        user.setUsername(username);

        String passwordHash = new BCryptPasswordEncoder().encode(password);
        user.setPasswordHash(passwordHash);

        userRepository.save(user);

        return new ApiResponse(ResponseData.SUCCESS, ResponseData.SUCCESS_MESSAGE);
    }

    public LoginResponse loginUser(String username, String password) throws IllegalAccessException {

        Optional<User> userObj = userRepository.findFirstByUsernameIgnoreCase(username);

        if(!userObj.isPresent()){
            throw new IllegalArgumentException(ResponseData.USER_VALIDATION_ERROR_MESSAGE);
        }

        User user = userObj.get();
        boolean passwordMatch = new BCryptPasswordEncoder().matches(password, user.getPasswordHash());

        if(!passwordMatch){
            throw new IllegalAccessException(ResponseData.USER_VALIDATION_ERROR_MESSAGE);
        }

        String token = UUID.randomUUID().toString();  // TODO tokenize user basic data
        authProvider.setAuthDetail(token, new AuthDetail(user.getUuid().toString()));

        LoginResponse response = new LoginResponse(token);
        response.setCode(ResponseData.SUCCESS);
        response.setDescription(ResponseData.SUCCESS_MESSAGE);

        return response;
    }

    public Optional<User> getUserByUuid(String uuidString) {
        UUID uuid = UUID.fromString(uuidString);

        return userRepository.findFirstByUuid(uuid);
    }
}
