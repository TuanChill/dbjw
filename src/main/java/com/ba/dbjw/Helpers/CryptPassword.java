package com.ba.dbjw.Helpers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class CryptPassword {
    public static String encryptPassword(String password) {
        try {
            // Create a SHA-256 message digest object
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Convert the password string to bytes
            byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);

            // Generate the hashed password
            byte[] hashedBytes = digest.digest(passwordBytes);

            // Convert the hashed bytes to a hexadecimal representation
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashedBytes) {
                stringBuilder.append(String.format("%02x", b));
            }

            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean verifyPassword(String Password, String inputPassword) {
        try {
            // Create a SHA-256 message digest object
            String hashedPassword = encryptPassword(inputPassword);
            // Compare the hashed passwords
            return hashedPassword.equals(Password);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
