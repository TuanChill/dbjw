package com.ba.dbjw.Controllers.Customer;

import com.ba.dbjw.Models.Enums.Gender;
import com.ba.dbjw.Service.Customer.CustomerServiceImp;
import com.ba.dbjw.Views.SceneController;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

// parent class with changes for Customer
public class ChangeCustomerController implements Initializable {
    @FXML
    protected Text errText;
    @FXML
    protected TextField nameCustomer;
    @FXML
    protected ChoiceBox<String> gender;
    @FXML
    protected DatePicker birthDate;
    @FXML
    protected TextField phoneNumber;
    @FXML
    protected TextField email;
    @FXML
    protected TextField address;
    protected final String[] genderList = {Gender.FEMALE.getDisplayName(), Gender.MALE.getDisplayName(), Gender.UNKNOWN.getDisplayName()};

    protected final CustomerServiceImp customerService = new CustomerServiceImp();


    protected boolean customerIsExist(String phoneNumber) {
        return customerService.checkCustomerIsExist(phoneNumber);
    }

    protected boolean isPhoneNumber(String input) {
        String phoneNumberRegex = "(84|0[3|5|7|8|9|1])+([0-9]{8})\\b";
        Pattern pattern = Pattern.compile(phoneNumberRegex);
        return pattern.matcher(input).matches();
    }

    protected boolean isEmail(String input) {
        String emailRegex = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(input).matches();
    }

    protected boolean checkBirthDate(LocalDate input) {
        if(input == null) return false;
        return !input.isBefore(LocalDate.now());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // define choose item
        gender.getItems().addAll(genderList);

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
