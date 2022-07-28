package com.parceldelivery.parcelorder.model.enums;

public enum Status {
    CREATE("1"), APPROVE("2"), MOVE("3"), DELIVERED("4"), CANCELED("5");

    private String code;

    private Status(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
