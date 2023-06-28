package com.ba.dbjw.Controllers;

import com.ba.dbjw.Controllers.PopupWindow.NewWindowController;
import com.ba.dbjw.Helpers.CurrentEntity.CurrentUser;
import com.ba.dbjw.Helpers.UpdateStatus.UpdateStatusProduct;
import com.ba.dbjw.Views.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


// parent class controller
public class DashController {

    @FXML
    protected AnchorPane rootPane;

    @FXML
    protected Label title;

    @FXML
    protected Label date;

    @FXML
    protected Label stats;

    @FXML
    protected Label updateTime;

    @FXML
    protected Label userInfo;

    @FXML
    protected TextField searchBar;

    @FXML
    protected void newWindow(ActionEvent event) throws IOException {
        NewWindowController.getNewProductWindow();
        if (UpdateStatusProduct.isProductAdded()) {
            refreshScreen(event);
            UpdateStatusProduct.setIsProductAdded(false);
        }
    }

    protected void preventEnter() {
        rootPane.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER && !(event.getTarget() instanceof TextField || event.getTarget() instanceof TextArea)) {
                event.consume();
            }
        });
    }

    protected void setUserInfo() {
        userInfo.setText(String.format("Người dùng: %s", CurrentUser.getCurrentUser().getUserName()));
    }

    @FXML
    protected void refreshScreen(ActionEvent event) throws IOException {
        SceneController.getProductDashScene(event);
    }

    @FXML
    protected void showCustomerScreen(ActionEvent event) throws IOException {
        SceneController.getCustomerDashScene(event);
    }

    @FXML
    protected void showInvoiceScreen(ActionEvent event) throws  IOException {
        SceneController.getInvoiceDashScene(event);
    }

    @FXML
    protected void showEmployeeScreen(ActionEvent event) throws IOException {
        SceneController.getEmployeeDashScene(event);
    }

    @FXML
    protected void showProductScreen(ActionEvent event) throws IOException {
        SceneController.getProductDashScene(event);
    }

    @FXML
    protected void logout(ActionEvent event) throws IOException {
        SceneController.getLoginScene(event);
    }

}
