package com.ba.dbjw.Controllers.Admin;

import com.ba.dbjw.Views.SceneController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AdminController {
    @FXML
    private AnchorPane content;
    @FXML
    private Button product_btn;
    @FXML
    private Button customer_btn;
    @FXML
    private Button invoice_btn;
    @FXML
    private Button revenue_btn;
    @FXML
    private Button voucher_btn;
    @FXML
    private Button staff_btn;
    @FXML
    private Button logout_btn;

    private static final Button[] currentScene = new Button[1];


    // handle logout when click btn logout
    @FXML
    public void logoutHandler(ActionEvent e) throws IOException {
        SceneController.getLoginScene(e);
    }

    public void handleClick(Button clickedBtn) {
        String style = "-fx-border-color: red; -fx-border-width: 0 0 0 5;";
        if(currentScene[0] != null ) {
            currentScene[0].setStyle("-fx-border-color: none; -fx-border-width: none;");
            currentScene[0]= null;
        }
        if (clickedBtn != null) {
            clickedBtn.setStyle(style);
            currentScene[0] = clickedBtn;
        }
    }

    @FXML
    public void handleProductBtn(ActionEvent event) {
        handleClick((Button) event.getSource());

        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/addProduct.fxml"));
            Parent fxmlRoot = loader.load();

            // Get the current children of the AnchorPane
            ObservableList<Node> children = content.getChildren();

            // Clear the existing children
            children.clear();

            // Add the loaded FXML root as a child to the AnchorPane
            children.add(fxmlRoot);

            //set layout for children root
            AnchorPane.setTopAnchor(fxmlRoot, 0.0);
            AnchorPane.setRightAnchor(fxmlRoot, 0.0);
            AnchorPane.setBottomAnchor(fxmlRoot, 0.0);
            AnchorPane.setLeftAnchor(fxmlRoot, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleCustomBtn(ActionEvent event) {
        handleClick((Button) event.getSource());

    }
}
