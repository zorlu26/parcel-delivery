package com.parceldelivery.parcelapigateway.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private long id;
    private String username;
    private String role;
}
