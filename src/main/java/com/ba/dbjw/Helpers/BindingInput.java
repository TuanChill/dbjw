package com.ba.dbjw.Helpers;

import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

import java.util.function.UnaryOperator;

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
}
