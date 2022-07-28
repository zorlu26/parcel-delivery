package com.parceldelivery.parcelapigateway.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AuthenticationStatus {
    private Boolean isAuthenticated;
    private String message;
    private Long userId;
    private String role;
}
