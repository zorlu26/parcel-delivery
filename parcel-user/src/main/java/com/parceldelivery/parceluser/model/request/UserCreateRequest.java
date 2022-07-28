package com.parceldelivery.parceluser.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequest {
    private String name;
    private String surname;
    private String username;
    private String password;
}
