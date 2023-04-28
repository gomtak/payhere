package com.payhere.presentation.controller;

import com.payhere.application.UserService;
import com.payhere.presentation.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity signUp(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(userService.saveUser(signUpRequest));
    }
}
