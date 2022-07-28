package com.parceldelivery.parcelorder.model.enums;

public enum Role {
    USER("USER"), ADMIN("ADMIN"), COURIER("COURIER");

    private String value;

    private Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
