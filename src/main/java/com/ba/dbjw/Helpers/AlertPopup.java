package com.ba.dbjw.Helpers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

public class AlertPopup {
    public static void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static String dialogPay(String headerText, Boolean isBinding) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Nhập thông tin");
        dialog.setHeaderText(headerText);
        dialog.setContentText("Giá trị:");

        if (isBinding) {
            dialog.getDialogPane().lookupButton(ButtonType.OK).disableProperty().bind(dialog.getEditor().textProperty().isEmpty());
        }

        dialog.showAndWait().ifPresent(result -> {
            if (result.isEmpty()) {
                showAlert("Lỗi", "Không được để trống giá trị!", Alert.AlertType.ERROR);
            } else {
                showAlert("Thông tin", "Giá trị đã nhập: " + result, Alert.AlertType.INFORMATION);
            }
        });
        return dialog.getResult();
    }
}
