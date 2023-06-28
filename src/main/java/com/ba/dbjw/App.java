package com.ba.dbjw;

import com.ba.dbjw.Utils.HibernateUtil;
import com.ba.dbjw.Views.SceneController;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.hibernate.Session;

public class App extends Application {
    @Override
    public void init() throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.close();
    }

    @Override
    public void start(Stage stage) throws Exception {
        SceneController.getInitialScene(stage);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        HibernateUtil.shutdown();
    }
    public static void main(String[] args) {
        launch();
    }
}
