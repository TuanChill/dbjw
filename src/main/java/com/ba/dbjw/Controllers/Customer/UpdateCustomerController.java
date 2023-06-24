package com.ba.dbjw.Controllers.Customer;

import com.ba.dbjw.Entity.Customer.Customer;
import com.ba.dbjw.Helpers.CurrentCustomer;
import com.ba.dbjw.Helpers.UpdateStatus.UpdateStatusCustomer;
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
import java.util.ResourceBundle;

import static com.ba.dbjw.Helpers.BindingInput.*;

public class UpdateCustomerController implements Initializable {
    @FXML
    private Text errText;
    @FXML
    private TextField codeCustomer;
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
            Customer customer = new Customer();
            // set value for obj
            customer.setCode(codeCustomer.getText());
            customer.setName(nameCustomer.getText().trim());
            customer.setGender(gender.getValue());
            customer.setPhoneNumber(phoneNumber.getText().trim());
            customer.setBirthday(birthDate.getValue());
            customer.setAddress(address.getText().trim());
            customer.setEmail(email.getText().trim());
            errText.setText("Đang cập nhật Khách hàng....");
            if (customerService.updateCustomer(customer)) {
                UpdateStatusCustomer.setIsCustomerAdded(true);
                errText.setText("Cập nhật khách hàng thành công");
                UpdateStatusCustomer.setIsCustomerAdded(true);
                delayWindowClose(event);
            } else {
                errText.setText("Đã có lỗi xảy ra");
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


    private void setCurrCustomer() {
        Customer currCustomer = CurrentCustomer.getCurrentCustomer();
        codeCustomer.setText(currCustomer.getCode());
        nameCustomer.setText(currCustomer.getName());
        gender.setValue(currCustomer.getGender());
        birthDate.setValue(currCustomer.getBirthday());
        phoneNumber.setText(currCustomer.getPhoneNumber());
        email.setText(currCustomer.getEmail());
        address.setText(currCustomer.getAddress());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // define choose item
        gender.getItems().addAll(genderList);

        setCurrCustomer();

    }

    private void delayWindowClose(ActionEvent event) {
        PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
        delay.setOnFinished(e -> cancelWindow(event));
        delay.play();
    }

    @FXML
    private void cancelWindow(ActionEvent event) {
        SceneController.close(event);
    }
}
