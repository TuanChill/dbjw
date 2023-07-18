package com.ba.dbjw.Controllers.Product;

import com.ba.dbjw.Entity.Product.Product;
import com.ba.dbjw.Helpers.CurrentProduct;
import com.ba.dbjw.Helpers.UpdateStatus.UpdateStatusProduct;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static com.ba.dbjw.Helpers.BindingInput.isNumeric;
import static com.ba.dbjw.Helpers.LazyLoading.loadAndDisplayImage;

public class UpdateProductController extends ChangeProductController {
    @FXML
    protected TextField codeProduct;

    @FXML
    public void submitHandler(ActionEvent event) {
        if (validateInput()) {
            Product product = Product.builder()
                    .code(codeProduct.getText())
                    .name(nameProduct.getText().trim())
                    .price(Long.parseLong(price.getText()))
                    .description(desc.getText().trim())
                    .stock(Integer.parseInt(stock.getText()))
                    .category(typeProduct.getValue().trim())
                    .size(size.getText().trim())
                    .imgUrl(CurrentProduct.getCurrentProduct().getImgUrl())
                    .material(material.getValue().trim())
                    .build();
            if(fileImg != null) {
                product.setImgUrl(fileImg.getPath());
            }
            errText.setText("Đang cập nhật sản phẩm....");
            if(productService.updateProduct(product)) {
                UpdateStatusProduct.setIsProductAdded(true);
                errText.setText("Cập nhật sản phẩm thành công");
                delayWindowClose(event);
                unbindFormatter();

                // clear cache img
                imgPreview.setImage(null);
            } else {
                errText.setText("Đã có lỗi xảy ra");
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
        }  else if (stock.getText().trim().isEmpty()) {
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

    protected void setCurrProduct() {
        Product currProduct = CurrentProduct.getCurrentProduct();
        codeProduct.setText(currProduct.getCode());
        nameProduct.setText(currProduct.getName());
        price.setText(currProduct.getPrice().toString());
        material.setValue(currProduct.getMaterial());
        typeProduct.setValue(currProduct.getCategory());
        size.setText(currProduct.getSize());
        stock.setText(String.valueOf(currProduct.getStock()));
        desc.setText(currProduct.getDescription());
        loadAndDisplayImage(imgPreview,currProduct.getImgUrl());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // define choose item
        typeProduct.getItems().addAll(typeProductList);
        material.getItems().addAll(materialList);

        onlyNumberTextField();

        setCurrProduct();

    }
}
