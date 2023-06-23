package com.ba.dbjw.Helpers;

import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

import java.time.LocalDate;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class BindingInput {
    public static UnaryOperator<TextFormatter.Change> onlyNumber = change -> {
        String newText = change.getControlNewText();
        if (newText.matches("\\d*")) { // Chỉ cho phép các ký tự số
            return change;
        }
        return null;
    };

    public static TextFormatter textFormatterNumber() {
        return new TextFormatter<>(new IntegerStringConverter(), null, BindingInput.onlyNumber);
    }

    public static boolean isPhoneNumber(String input) {
        String phoneNumberRegex = "(84|0[3|5|7|8|9|1])+([0-9]{8})\\b";
        Pattern pattern = Pattern.compile(phoneNumberRegex);
        return pattern.matcher(input).matches();
    }

    public static boolean isEmail(String input) {
        String emailRegex = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(input).matches();
    }

    public static boolean isCCCD(String input) {
        String citizenID = "^[0-9]{12}$";
        Pattern pattern = Pattern.compile(citizenID);
        return pattern.matcher(input).matches();
    }

    public static boolean checkBirthDate(LocalDate input) {
        if(input == null) return false;
        return !input.isBefore(LocalDate.now());
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
