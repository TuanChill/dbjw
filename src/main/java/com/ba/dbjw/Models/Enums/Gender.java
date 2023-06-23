package com.ba.dbjw.Models.Enums;

public enum Gender {
    MALE("Nam"),
    FEMALE("Nữ"),
    UNKNOWN("Khác");
    private final String displayName;

    Gender(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
