package com.parceldelivery.parceluser.controller;

import com.parceldelivery.parceluser.model.request.UserLoginRequest;
import com.parceldelivery.parceluser.model.request.UserCourierCreateRequest;
import com.parceldelivery.parceluser.model.request.UserCreateRequest;
import com.parceldelivery.parceluser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity createUser(@RequestBody UserCreateRequest userCreateRequest) {
        return ResponseEntity.ok(userService.createUser(userCreateRequest));
    }

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody UserLoginRequest userLoginRequest) {

        return ResponseEntity.ok(userService.login(userLoginRequest));
    }

    @PostMapping(value = "/register-courier")
    public ResponseEntity createCourier(@RequestHeader Map<String, String> headers, @RequestBody UserCourierCreateRequest userCourierCreateRequest) throws Exception {

        return ResponseEntity.ok(userService.createCourier(headers, userCourierCreateRequest));
    }
}
