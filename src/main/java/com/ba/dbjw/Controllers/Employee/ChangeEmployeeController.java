package com.ba.dbjw.Controllers.Employee;

import com.ba.dbjw.Models.Enums.Gender;
import com.ba.dbjw.Service.Empoyee.EmployeeServiceImpl;
import com.ba.dbjw.Views.SceneController;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

// parent class for changes Employee
public class ChangeEmployeeController implements Initializable {
    @FXML
    protected Text errText;
    @FXML
    protected TextField nameEmployee;
    @FXML
    protected ChoiceBox<String> gender;
    @FXML
    protected ChoiceBox<String> position;
    @FXML
    protected DatePicker birthdate;
    @FXML
    protected TextField phoneNumber;
    @FXML
    protected TextField email;
    @FXML
    protected TextField cccd;
    @FXML
    protected TextField address;
    @FXML
    protected ImageView imgPreview;

    protected File fileImg;

    protected final String[] genderList = {Gender.FEMALE.getDisplayName(), Gender.MALE.getDisplayName(), Gender.UNKNOWN.getDisplayName()};

    protected final String[] positionList = {"Quản lý", "Trưởng quầy", "Tư vấn", "Thu Ngân", "Bảo vệ"};

    protected final EmployeeServiceImpl employeeService = new EmployeeServiceImpl();

    @FXML
    protected void imgChooser(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            imgPreview.setImage(new Image(file.getPath()));
            fileImg = file;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // define choose item
        gender.getItems().addAll(genderList);
        position.getItems().addAll(positionList);

    }

    protected void delayWindowClose(ActionEvent event) {
        PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
        delay.setOnFinished(e -> cancelWindow(event));
        delay.play();
    }

    @FXML
    protected void cancelWindow(ActionEvent event) {
        SceneController.close(event);
    }
}
