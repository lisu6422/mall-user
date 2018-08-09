package com.thoughtworks.malluser.service;

import com.thoughtworks.malluser.controller.request.LoginRequest;
import com.thoughtworks.malluser.entity.User;
import com.thoughtworks.malluser.exception.UserNotFoundException;
import com.thoughtworks.malluser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private UserRepository userRepository;

    @Autowired
    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUserName()).orElseThrow(()-> new UserNotFoundException());
        return user;
    }
}
