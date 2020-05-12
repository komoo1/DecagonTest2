package com.decagon.stock.service;

import com.decagon.stock.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * @author Victor.Komolafe
 */
@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

}
