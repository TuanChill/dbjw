package com.ba.dbjw.Controllers.Employee;

import com.ba.dbjw.Entity.Employee.Employee;
import com.ba.dbjw.Helpers.CurrentEntity.CurrentEmployee;
import com.ba.dbjw.Helpers.UpdateStatus.UpdateStatusEmployee;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static com.ba.dbjw.Helpers.BindingInput.*;
import static com.ba.dbjw.Helpers.BindingInput.checkBirthDate;
import static com.ba.dbjw.Helpers.LazyLoading.loadAndDisplayImage;


public class UpdateEmployeeController extends ChangeEmployeeController {
    @FXML
    private TextField codeEmployee;

    @FXML
    protected void submitHandler(ActionEvent event) {
        if (validateInput()) {
            errText.setText("Đang cập nhật nhân viên....");
            Employee employee = new Employee();
            // set value for obj
            employee.setCode(codeEmployee.getText());
            employee.setName(nameEmployee.getText());
            employee.setGender(gender.getValue());
            employee.setPosition(position.getValue());
            employee.setBirthday(birthdate.getValue());
            employee.setPhoneNumber(phoneNumber.getText());
            employee.setEmail(email.getText());
            employee.setCccd(cccd.getText());
            employee.setAddress(address.getText());
            employee.setAvatar(fileImg.toString());

            if (employeeService.updateEmployee(employee)) {
                UpdateStatusEmployee.setIsEmployeeAdded(true);
                errText.setText("Cập nhật nhân viên thành công");
                delayWindowClose(event);
            } else {
                errText.setText("Đã có lỗi xảy ra");
            }
        }
    }

    protected boolean validateInput() {
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

    private void setCurrEmployee() {
        Employee currEmployee = CurrentEmployee.getCurrentEmployee();
        codeEmployee.setText(currEmployee.getCode());
        nameEmployee.setText(currEmployee.getName());
        gender.setValue(currEmployee.getGender());
        position.setValue(currEmployee.getPosition());
        birthdate.setValue(currEmployee.getBirthday());
        phoneNumber.setText(currEmployee.getPhoneNumber());
        email.setText(currEmployee.getEmail());
        cccd.setText(currEmployee.getCccd());
        address.setText(currEmployee.getAddress());
        fileImg = new File(currEmployee.getAvatar());
        loadAndDisplayImage(imgPreview, currEmployee.getAvatar());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // define choose item
        gender.getItems().addAll(genderList);
        position.getItems().addAll(positionList);

        setCurrEmployee();
//
    }
}
