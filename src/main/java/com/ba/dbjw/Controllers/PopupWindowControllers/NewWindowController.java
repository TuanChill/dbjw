package com.ba.dbjw.Controllers.PopupWindowControllers;


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
        getPopUpWindow(ScenePath.PRODUCTADMIN.getPath());
    }

//    public static Stage getNewVetWindow() throws IOException {
//        Stage stage = new Stage();
//        Pane main = FXMLLoader.load(NewWindowController.class.getResource(ScenePath.ADD_VET.getPath()));
//        controlDrag(main, stage);
//        stage.setScene(new Scene(main));
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.initStyle(StageStyle.UNDECORATED);
//        stage.setTitle("Pet Clinic CRM");
//        stage.getScene();
//        stage.showAndWait();
//        return stage;
//    }

    public static void getPopUpWindow(String path) throws IOException {
        Stage stage = new Stage();
        Pane main = FXMLLoader.load(NewWindowController.class.getResource(path));
        controlDrag(main, stage);
        stage.setScene(new Scene(main));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Pet Clinic CRM");
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
