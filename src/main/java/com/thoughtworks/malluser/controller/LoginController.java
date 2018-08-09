package com.thoughtworks.malluser.controller;

import com.thoughtworks.malluser.controller.request.LoginRequest;
import com.thoughtworks.malluser.entity.User;
import com.thoughtworks.malluser.exception.UserNotFoundException;
import com.thoughtworks.malluser.login.JwtUser;
import com.thoughtworks.malluser.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.net.URI;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService userService;

    private JwtUser getUser() {
        return (JwtUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    @PostMapping
    public ResponseEntity login(@RequestBody LoginRequest loginRequest){
        User user = userService.login(loginRequest);
        String str = String.format("%s:%s", user.getUsername(), user.getPassword());
        String token = Base64Coder.encodeString(str);
        return ResponseEntity.created(URI.create(String.format("/login/%s", token))).build();
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void orderNotFoundExceptionHandler(UserNotFoundException ex) {

    }
}
