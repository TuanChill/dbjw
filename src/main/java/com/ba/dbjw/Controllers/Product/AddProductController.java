package com.ba.dbjw.Controllers.Product;

import com.ba.dbjw.Entity.Product.Product;
import com.ba.dbjw.Helpers.BindingInput;
import com.ba.dbjw.Helpers.UpdateStatus.UpdateStatusCustomer;
import com.ba.dbjw.Helpers.UpdateStatus.UpdateStatusProduct;
import com.ba.dbjw.Service.Product.ProductServiceImpl;
import com.ba.dbjw.Views.SceneController;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static com.ba.dbjw.Helpers.BindingInput.isNumeric;

public class AddProductController implements Initializable {
    @FXML
    private Text errText;
    @FXML
    private TextField nameProduct;
    @FXML
    private TextField price;
    @FXML
    private ChoiceBox<String> typeProduct;
    @FXML
    private TextArea desc;
    @FXML
    private TextField size;
    @FXML
    private TextField stock;
    @FXML
    private ImageView imgPreview;
    @FXML
    private ChoiceBox<String> material;

    private File fileImg;

    private final String[] typeProductList = {"Nhẫn", "Bông tai ", "Dây chuyền"};

    private final String[] materialList = {"Bạc", "Vàng", "Kim Cương"};

    private final ProductServiceImpl productService = new ProductServiceImpl();


    @FXML
    public void submitHandler(ActionEvent event) {
        if (validateInput()) {
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
            if(productService.createProduct(product)) {
                UpdateStatusProduct.setIsProductAdded(true);
                errText.setText("Lưu sản phẩm thành công");
                delayWindowClose(event);
                unbindFormatter();
            } else {
                errText.setText("Đã có lỗi xảy ra");
            }
        }
    }

    private boolean validateInput() {
        errText.setText("");
        if(nameProduct.getText().isEmpty()) {
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

    //TextField bindings only enter numbers
    private void onlyNumberTextField() {
        price.setTextFormatter(BindingInput.textFormatterNumber());
        stock.setTextFormatter(BindingInput.textFormatterNumber());
    }

    private void unbindFormatter() {
        price.setTextFormatter(null);
        stock.setTextFormatter(null);
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
        typeProduct.getItems().addAll(typeProductList);
        material.getItems().addAll(materialList);

        onlyNumberTextField();
    }

    private void delayWindowClose(ActionEvent event) {
        PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
        delay.setOnFinished(e -> cancelWindow(event));
        delay.play();
    }

    @FXML
    private void cancelWindow(ActionEvent event) {
        unbindFormatter();
        SceneController.close(event);
    }
}
