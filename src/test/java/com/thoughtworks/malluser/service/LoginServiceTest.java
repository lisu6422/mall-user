package com.thoughtworks.malluser.service;

import com.thoughtworks.malluser.controller.request.LoginRequest;
import com.thoughtworks.malluser.entity.User;
import com.thoughtworks.malluser.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class LoginServiceTest {
    private LoginService loginService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() {
        loginService = new LoginService(userRepository);
    }

    @Test
    public void test_login(){
        // when
        LoginRequest loginRequest = LoginRequest.builder().userName("user1").password("password1").build();
        User user = User.builder().id(1).username("user1").password("$2a$10$XI5pX3v5N0izBWJx95W04eyyNKhkT4ObCV0OQ39GSEujE8PrXaK6a").role("ADMIN").build();
        when(userRepository.findByUsername(loginRequest.getUserName())).thenReturn(Optional.of(user));

        // given
        User result = loginService.login(loginRequest);

        // then
        assertEquals(1, result.getId());
        assertEquals("user1", result.getUsername());
    }
}