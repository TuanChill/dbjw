package com.ba.dbjw.Controllers.Product;

import com.ba.dbjw.Entity.Product.Product;
import com.ba.dbjw.Helpers.UpdateStatus.UpdateStatusProduct;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


import static com.ba.dbjw.Helpers.BindingInput.isNumeric;

public class AddProductController extends ChangeProductController {
    @FXML
    public void submitHandler(ActionEvent event) {
        if (validateInput()) {
            submitBtn.setDisable(true);
            Product product = Product.builder()
                    .name(nameProduct.getText().trim())
                    .price(Long.parseLong(price.getText()))
                    .description(desc.getText().trim())
                    .stock(Integer.parseInt(stock.getText()))
                    .category(typeProduct.getValue().trim())
                    .size(size.getText().trim())
                    .material(material.getValue().trim())
                    .imgUrl(fileImg.getPath())
                    .build();
            errText.setText("Đang lưu sản phẩm....");
            if (productService.createProduct(product)) {
                UpdateStatusProduct.setIsProductAdded(true);
                errText.setText("Lưu sản phẩm thành công");
                delayWindowClose(event);
                unbindFormatter();

                // clear cache img
                imgPreview.setImage(null);
            } else {
                errText.setText("Đã có lỗi xảy ra");
                submitBtn.setDisable(false);
            }
        }
    }

    protected boolean validateInput() {
        errText.setText("");
        if (nameProduct.getText().isEmpty()) {
            errText.setText("Tên sản phẩm không được bỏ trống");
            return false;
        } else if (price.getText().trim().isEmpty()) {
            errText.setText("Giá bán ra không được bỏ trống");
            return false;
        } else if (isNumeric(price.getText().trim()) && Long.parseLong(price.getText().trim()) <= 0) {
            errText.setText("Vui lòng nhập đúng giá bán ra của sản phẩm");
            return false;
        } else if (typeProduct.getValue() == null) {
            errText.setText("Vui lòng chọn loại sản phẩm");
            return false;
        } else if (material.getValue() == null) {
            errText.setText("Vui lòng chọn chất liệu");
            return false;
        } else if (fileImg == null) {
            errText.setText("Vui lòng chọn ảnh cho sản phẩm");
            return false;
        } else if (stock.getText().trim().isEmpty()) {
            errText.setText("Số lượng sản phẩm không được bỏ trống");
            return false;
        } else if (isNumeric(stock.getText().trim()) && Long.parseLong(stock.getText().trim()) <= 0) {
            errText.setText("Vui lòng nhập đúng số lượng");
            return false;
        } else if (desc.getText().trim().isEmpty()) {
            errText.setText("Mô tả sản phẩm không được bỏ trống");
            return false;
        } else {
            return true;
        }
    }
}
