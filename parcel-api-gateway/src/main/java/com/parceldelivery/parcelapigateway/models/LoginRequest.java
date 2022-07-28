package com.parceldelivery.parcelapigateway.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    String username;
    String password;
}
