package com.ba.dbjw.Controllers.Employee;

import com.ba.dbjw.Entity.Employee.Employee;
import com.ba.dbjw.Entity.Product.Product;
import com.ba.dbjw.Helpers.BindingInput;
import com.ba.dbjw.Helpers.UpdateStatus.UpdateStatusEmployee;
import com.ba.dbjw.Helpers.UpdateStatus.UpdateStatusProduct;
import com.ba.dbjw.Models.Enums.Gender;
import com.ba.dbjw.Service.Empoyee.EmployeeServiceImpl;
import com.ba.dbjw.Service.Product.ProductServiceImpl;
import com.ba.dbjw.Views.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static com.ba.dbjw.Helpers.BindingInput.*;

public class AddEmployeeController implements Initializable {
    @FXML
    private Text errText;
    @FXML
    private TextField nameEmployee;
    @FXML
    private ChoiceBox<String> gender;
    @FXML
    private ChoiceBox<String> position;
    @FXML
    private DatePicker birthdate;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField email;
    @FXML
    private TextField cccd;
    @FXML
    private TextField address;
    @FXML
    private ImageView imgPreview;

    private File fileImg;

    private final String[] genderList = {Gender.FEMALE.getDisplayName(), Gender.MALE.getDisplayName(), Gender.UNKNOWN.getDisplayName()};

    private final String[] positionList = {"Quản lý", "Trưởng quầy", "Tư vấn", "Thu Ngân", "Bảo vệ"};

    private final EmployeeServiceImpl employeeService = new EmployeeServiceImpl();


    @FXML
    public void submitHandler(ActionEvent event) {
        if (validateInput()) {
            if(!employeeService.checkEmployeeExist(cccd.getText())) {
                Employee employee = new Employee();
                // set value for obj
                employee.setName(nameEmployee.getText());
                employee.setGender(gender.getValue());
                employee.setPosition(position.getValue());
                employee.setBirthday(birthdate.getValue());
                employee.setPhoneNumber(phoneNumber.getText());
                employee.setEmail(email.getText());
                employee.setCccd(cccd.getText());
                employee.setAddress(address.getText());
                employee.setAvatar(fileImg.toString());

                cancelWindow(event);
                employeeService.createEmployee(employee);
                UpdateStatusEmployee.setIsCustomerAdded(true);
            } else {
                errText.setText("Nhân viên đã tồn tại(CCCD trùng)!");
            }
        }
    }

    private boolean validateInput() {
        errText.setText("");
        if (nameEmployee.getText().isEmpty()) {
            errText.setText("Tên Nhân viên không được bỏ trống");
            return false;
        } else if (phoneNumber.getText().trim().isEmpty()) {
            errText.setText("Số điện thoại không được bỏ trống");
            return false;
        } else if (!isPhoneNumber(phoneNumber.getText())) {
            errText.setText("Vui lòng nhập đúng số điện thoại");
            return false;
        } else if (gender.getValue() == null) {
            errText.setText("Vui lòng chọn giới tính");
            return false;
        } else if (position.getValue() == null) {
            errText.setText("Vui lòng chọn chức vụ");
            return false;
        } else if (fileImg == null) {
            errText.setText("Vui lòng chọn ảnh cho nhân viên");
            return false;
        } else if (cccd.getText().trim().isEmpty()) {
            errText.setText("Căn cước công dân không được bỏ trống");
            return false;
        } else if (!isCCCD(cccd.getText())) {
            errText.setText("Vui lòng nhập đúng Căn cước công dân");
            return false;
        } else if (email.getText().isEmpty()) {
            errText.setText("Vui lòng nhập Email");
            return false;
        } else if (!isEmail(email.getText())) {
            errText.setText("Vui lòng nhập đúng Email");
            return false;
        } else if (address.getText().isEmpty()) {
            errText.setText("Địa chỉ không được bỏ trống");
            return false;
        } else if (checkBirthDate(birthdate.getValue())) {
            errText.setText("Ngày sinh không hợp lệ");
            return false;
        } else {
            return true;
        }
    }

    @FXML
    public void imgChooser(ActionEvent event) {
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

    @FXML
    private void cancelWindow(ActionEvent event) {
        SceneController.close(event);
    }
}
