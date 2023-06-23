package com.ba.dbjw.Helpers;

import java.util.UUID;

public class UUIDGenerator {
    public static String shortUUID() {
        // Generate a random UUID
        UUID uuid = UUID.randomUUID();

        // Convert the UUID to a hex string and take the first 6 characters
        return uuid.toString().replaceAll("-", "").substring(0, 6).toUpperCase();
    }
}
