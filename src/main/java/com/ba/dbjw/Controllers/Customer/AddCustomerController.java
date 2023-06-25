package com.ba.dbjw.Controllers.Customer;

import com.ba.dbjw.Entity.Customer.Customer;
import com.ba.dbjw.Helpers.UpdateStatus.UpdateStatusCustomer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class AddCustomerController extends ChangeCustomerController {
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
                errText.setText("Đang lưu Khách hàng....");
                if(customerService.createCustomer(customer)) {
                    UpdateStatusCustomer.setIsCustomerAdded(true);
                    errText.setText("Lưu khách hàng thành công");
                    delayWindowClose(event);
                } else {
                    errText.setText("Đã có lỗi xảy ra");
                }
            } else {
                errText.setText("Khách hàng đã tồn tại");
            }
        }
    }


    protected boolean validateInput() {
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
}
