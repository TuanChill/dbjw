package com.ba.dbjw.Controllers.Employee;

import com.ba.dbjw.Entity.Employee.Employee;
import com.ba.dbjw.Helpers.CurrentEntity.CurrentEmployee;
import com.ba.dbjw.Helpers.UpdateStatus.UpdateStatusEmployee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import lombok.AllArgsConstructor;

import java.net.URL;
import java.util.ResourceBundle;

@AllArgsConstructor

public class UpdateEmployeeController extends AddEmployeeController {
    @FXML
    private TextField codeEmployee;


    @FXML
    @Override
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

    private void setCurrEmployee() {
        Employee currEmployee = CurrentEmployee.getCurrentEmployee();
        codeEmployee.setText(currEmployee.getCode());
        nameEmployee.setText(currEmployee.getName());
        gender.setValue(currEmployee.getGender());
        position.setValue(currEmployee.getPosition());
        birthdate.setValue(currEmployee.getBirthday());
        email.setText(currEmployee.getEmail());
        cccd.setText(currEmployee.getCccd());
        address.setText(currEmployee.getAddress());
        imgPreview.setImage(new Image(currEmployee.getAvatar()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // define choose item
        gender.getItems().addAll(genderList);
        position.getItems().addAll(positionList);

        setCurrEmployee();
    }
}
