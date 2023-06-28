package com.ba.dbjw.Controllers;

import com.ba.dbjw.Entity.UserAuth.UserAuth;
import com.ba.dbjw.Helpers.CurrentEntity.CurrentUser;
import com.ba.dbjw.Service.UserAuth.UserServiceImpl;
import com.ba.dbjw.Views.SceneController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Objects;

public class LoginController {

    // define elements of Login Scene
    @FXML
    private TextField userNameInput;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Text errText;
    @FXML
    private Button submitButton;
    @FXML
    private Text forgotPassword;

    public void submitHandler(ActionEvent e) throws IOException {
        String userName = userNameInput.getText().trim();
        String password = passwordField.getText().trim();

        // reset err Text
        errText.setText("");

        if (userName.isEmpty()) {
            errText.setText("Vui lòng nhập tên tài khoản");
        } else if (password.isEmpty()) {
            errText.setText("Vui lòng nhập mật khẩu");
        } else {
            // validate user login
            UserServiceImpl userService = new UserServiceImpl();
            UserAuth user = userService.login(userName, password);
            if (user == null) {
                errText.setText("Tài khoản hoặc mật khẩu không đúng");
            } else {
                if(Objects.equals(user.getRole(), "admin")) {
                    CurrentUser.setCurrentUser(user);
                    SceneController.getInvoiceDashScene(e);
                } else {
                    SceneController.getInvoiceDashScene(e);
                }
            }
        }
    }

    public void handleForgotBtn(MouseEvent event) {

    }
}