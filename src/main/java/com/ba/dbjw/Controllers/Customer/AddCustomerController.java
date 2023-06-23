package com.ba.dbjw.Controllers.Customer;

import com.ba.dbjw.Entity.Customer.Customer;
import com.ba.dbjw.Helpers.UpdateStatus.UpdateStatusCustomer;
import com.ba.dbjw.Models.Enums.Gender;
import com.ba.dbjw.Service.Customer.CustomerServiceImp;
import com.ba.dbjw.Views.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddCustomerController implements Initializable {
    @FXML
    private Text errText;
    @FXML
    private TextField nameCustomer;
    @FXML
    private ChoiceBox<String> gender;
    @FXML
    private DatePicker birthDate;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField email;
    @FXML
    private TextField address;
    private final String[] genderList = {Gender.FEMALE.getDisplayName(), Gender.MALE.getDisplayName(), Gender.UNKNOWN.getDisplayName()};

    private final CustomerServiceImp customerService = new CustomerServiceImp();


    @FXML
    public void submitHandler(ActionEvent event) {
        if (validateInput()) {
            if (!customerIsExist(phoneNumber.getText())) {
                Customer customer = new Customer();
                // set value for obj
                customer.setName(nameCustomer.getText().trim());
                customer.setGender(gender.getValue());
                customer.setPhoneNumber(phoneNumber.getText().trim());
                customer.setBirthday(birthDate.getValue());
                customer.setAddress(address.getText().trim());
                customer.setEmail(email.getText().trim());
                cancelWindow(event);
                customerService.createCustomer(customer);
                UpdateStatusCustomer.setIsCustomerAdded(true);
            } else {
                errText.setText("Khách hàng đã tồn tại");
            }
        }
    }

    private boolean validateInput() {
        errText.setText("");
        if (nameCustomer.getText().isEmpty()) {
            errText.setText("Tên Khách hàng không được bỏ trống");
            return false;
        } else if (gender.getValue() == null) {
            errText.setText("Vui lòng chọn loại giới tính");
            return false;
        } else if (phoneNumber.getText().trim().isEmpty()) {
            errText.setText("Số điện thoại không được bỏ trống");
            return false;
        } else if (!isPhoneNumber(phoneNumber.getText())) {
            errText.setText("Số điện thoại không hợp lệ");
            return false;
        } else if (!isEmail(email.getText())) {
            errText.setText("Email không hợp lệ");
            return false;
        } else if (address.getText().trim().isEmpty()) {
            errText.setText("Địa chỉ không được bỏ trống");
            return false;
        } else if (checkBirthDate(birthDate.getValue())) {
            errText.setText("Ngày sinh không hợp lệ");
            return false;
        } else {
            return true;
        }
    }


    private boolean customerIsExist(String phoneNumber) {
        return customerService.checkCustomerIsExist(phoneNumber);
    }

    private boolean isPhoneNumber(String input) {
        String phoneNumberRegex = "(84|0[3|5|7|8|9|1])+([0-9]{8})\\b";
        Pattern pattern = Pattern.compile(phoneNumberRegex);
        return pattern.matcher(input).matches();
    }

    private boolean isEmail(String input) {
        String emailRegex = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(input).matches();
    }

    private boolean checkBirthDate(LocalDate input) {
        if(input == null) return false;
        return !input.isBefore(LocalDate.now());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // define choose item
        gender.getItems().addAll(genderList);

    }

    @FXML
    private void cancelWindow(ActionEvent event) {
        SceneController.close(event);
    }
}
