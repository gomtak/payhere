package com.payhere.presentation.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
    private String phoneNumber;
    private String password;
}
