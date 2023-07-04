package com.ba.dbjw.Controllers.Employee;

import com.ba.dbjw.Entity.Employee.Employee;
import com.ba.dbjw.Helpers.UpdateStatus.UpdateStatusEmployee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import static com.ba.dbjw.Helpers.BindingInput.*;

public class AddEmployeeController extends ChangeEmployeeController {
    @FXML
    protected void submitHandler(ActionEvent event) {
        if (validateInput()) {
            if(!employeeService.checkEmployeeExist(cccd.getText())) {
                errText.setText("Đang lưu nhân viên....");
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

                if(employeeService.createEmployee(employee)) {
                    UpdateStatusEmployee.setIsEmployeeAdded(true);
                    errText.setText("Lưu nhân viên thành công");
                    delayWindowClose(event);

                    // clear cache img
                    imgPreview.setImage(null);
                } else {
                    errText.setText("Đã có lỗi xảy ra");
                }
            } else {
                errText.setText("Nhân viên đã tồn tại(CCCD trùng)!");
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
}
