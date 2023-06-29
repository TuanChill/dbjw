package com.ba.dbjw.Models.Enums;

public enum Role {
    ADMIN("admin"),
    MANAGER("manager"),
    USER("user");
    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getDisplayName() {
        return value;
    }
}
