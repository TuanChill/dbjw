package com.ba.dbjw.Controllers.UserAuth;

import com.ba.dbjw.Entity.UserAuth.UserAuth;
import com.ba.dbjw.Models.Enums.Role;
import com.ba.dbjw.Service.UserAuth.UserService;
import com.ba.dbjw.Service.UserAuth.UserServiceImpl;
import com.ba.dbjw.Views.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

import static com.ba.dbjw.Helpers.AlertPopup.dialogPay;
import static com.ba.dbjw.Helpers.AlertPopup.showAlert;

public class RegisterController implements Initializable {
    @FXML
    private TextField userName;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField rePassword;

    @FXML
    private ChoiceBox<String> roleList;

    @FXML
    private Text errText;

    private final String[] roles = {Role.MANAGER.getDisplayName(), Role.USER.getDisplayName()};


    private final UserService<UserAuth> userService = new UserServiceImpl();

    @FXML
    private void submitHandler(ActionEvent event) {
        if (validateInput()) {
            if (checkAdmin()) {
                UserAuth user = UserAuth.builder()
                        .userName(userName.getText())
                        .password(password.getText())
                        .role(roleList.getValue())
                        .build();
                if (userService.register(user)) {
                    showAlert("Thông báo", "Tạo tài khoản thành công", Alert.AlertType.INFORMATION);
                    SceneController.close(event);
                } else {
                    showAlert("Lỗi", "Tài khoản đã tồn tại", Alert.AlertType.INFORMATION);
                }
            }
        }
    }

    private boolean validateInput() {
        errText.setText("");
        if (userName.getText().isEmpty()) {
            errText.setText("Tên tài khoản không được để trống");
            return false;
        } else if (password.getText().isEmpty()) {
            errText.setText("Mật khẩu không được bỏ trống");
            return false;
        } else if (rePassword.getText().isEmpty()) {
            errText.setText("Vui lòng nhập lại mật khẩu");
            return false;
        } else if (!rePassword.getText().equals(password.getText())) {
            errText.setText("Mật khẩu không giống nhau");
            return false;
        } else {
            return true;
        }
    }

    private boolean checkAdmin() {
        if (roleList.getValue().equals("manager")) {
            String userNameAd = dialogPay("Nhập tên tài khoản", true);
            String passwordAd = dialogPay("Nhập mật khẩu", true);
            UserAuth userAd = userService.login(userNameAd, passwordAd);
            if (userAd != null && userAd.getRole().equals("admin")) {
                return true;
            } else {
                showAlert("Lỗi", "Xác thực admin không đúng", Alert.AlertType.ERROR);
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // default role
        roleList.setValue(Role.USER.getDisplayName());

        //set roleList
        roleList.getItems().addAll(roles);
    }
}
