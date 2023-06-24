package com.ba.dbjw.Controllers.PopupWindow;


import com.ba.dbjw.Helpers.ScenePath;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
public class NewWindowController {

    static double x;
    static double y;

    public static void getNewProductWindow() throws IOException {
        getPopUpWindow(ScenePath.ADDPRODUCT.getPath());
    }

    public static void getUpdateProductWindow() throws IOException {
        getPopUpWindow(ScenePath.UPDATEPRODUCT.getPath());
    }


    public static void getNewCustomerWindow() throws IOException {
        getPopUpWindow(ScenePath.ADDCUSTOMER.getPath());
    }

    public static void getUpdateCustomerWindow() throws IOException {
        getPopUpWindow(ScenePath.UPDATECUSTOMER.getPath());
    }


    public static void getNewEmployeeWindow() throws IOException {
        getPopUpWindow(ScenePath.ADDEMPLOYEE.getPath());
    }


    public static void getPopUpWindow(String path) throws IOException {
        Stage stage = new Stage();
        Pane main = FXMLLoader.load(NewWindowController.class.getResource(path));
        controlDrag(main, stage);
        stage.setScene(new Scene(main));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Quản lý cửa hàng trang sức");
        stage.getScene();
        stage.showAndWait();
    }

    public static void controlDrag(Pane main, Stage stage) {
        main.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                x = stage.getX() - event.getScreenX();
                y = stage.getY() - event.getScreenY();
            }
        });
        main.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() + x);
                stage.setY(event.getScreenY() + y);
            }
        });
    }
}
