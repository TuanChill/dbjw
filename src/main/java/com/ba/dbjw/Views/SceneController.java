package com.ba.dbjw.Views;

import com.ba.dbjw.Helpers.ScenePath;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneController {

    private static double x;
    private static double y;

    private static Parent main;

    public static void getInitialScene(Stage stage) throws IOException {

        main = FXMLLoader.load((SceneController.class.getResource(ScenePath.LOGIN.getPath())));
        Scene scene = new Scene(main);
        stage.setTitle("Quản lý cửa hàng trang sức");
        // disable resizing window
        stage.setScene(scene);
        stage.setResizable(false); // disable resizing
        stage.setMaximized(false); // disable maximizing
        stage.setScene(scene);
        stage.show();

        //set icon app
        stage.getIcons().add(new Image(Objects.requireNonNull(SceneController.class.getResourceAsStream("/Assets/Images/img-logo.png"))));
    }

    public static void getLoginScene(ActionEvent event) throws  IOException {
        changeScreen(event, ScenePath.LOGIN.getPath());
    }

    public static void getProductDashScene(ActionEvent event) throws  IOException {
        changeScreen(event, ScenePath.PRODUCTDASH.getPath());
    }


    public static void getCustomerDashScene(ActionEvent event) throws IOException {
        changeScreen(event, ScenePath.CUSTOMERDASH.getPath());
    }

    public static void getEmployeeDashScene(ActionEvent event) throws IOException {
        changeScreen(event, ScenePath.EMPLOYEEDASH.getPath());
    }

    public static void getInvoiceDashScene(ActionEvent event) throws IOException {
        changeScreen(event, ScenePath.INVOICEDASH.getPath());
    }

    private static void changeScreen(ActionEvent event, String path) throws IOException {
        main = FXMLLoader.load(SceneController.class.getResource(path));
        Scene visitScene = new Scene(main);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        controlDrag(window);
        window.setScene(visitScene);
        window.show();
    }

    public static void controlDrag(Stage stage) {
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

    public static void close(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

}

