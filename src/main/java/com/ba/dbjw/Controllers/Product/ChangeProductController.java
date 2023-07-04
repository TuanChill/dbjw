package com.ba.dbjw.Controllers.Product;

import com.ba.dbjw.Helpers.BindingInput;
import com.ba.dbjw.Service.Product.ProductServiceImpl;
import com.ba.dbjw.Views.SceneController;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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


public class ChangeProductController implements Initializable {
    @FXML
    protected Text errText;
    @FXML
    protected TextField nameProduct;
    @FXML
    protected TextField price;
    @FXML
    protected ChoiceBox<String> typeProduct;
    @FXML
    protected TextArea desc;
    @FXML
    protected TextField size;
    @FXML
    protected TextField stock;
    @FXML
    protected ImageView imgPreview;
    @FXML
    protected ChoiceBox<String> material;

    protected File fileImg;

    protected final String[] typeProductList = {"Nhẫn", "Bông tai ", "Dây chuyền", "Lắc", "Kiềng", "Charm"};

    protected final String[] materialList = {"Bạc", "Vàng", "Kim Cương", "Platinum"};

    protected final ProductServiceImpl productService = new ProductServiceImpl();


    //TextField bindings only enter numbers
    protected void onlyNumberTextField() {
        price.setTextFormatter(BindingInput.textFormatterNumber());
        stock.setTextFormatter(BindingInput.textFormatterNumber());
    }

    protected void unbindFormatter() {
        price.setTextFormatter(null);
        stock.setTextFormatter(null);
    }

    @FXML
    public void imgChooser(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String filePath = file.getPath();
            String fileExtension = filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase();

            if (fileExtension.equals("png") || fileExtension.equals("jpg")) {
                imgPreview.setImage(new Image(filePath));
                fileImg = file;
            } else {
                // Thông báo lỗi: Chỉ chấp nhận file PNG hoặc JPG
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Ảnh chỉ nên là PNG/JPG !");
                alert.showAndWait();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // define choose item
        typeProduct.getItems().addAll(typeProductList);
        material.getItems().addAll(materialList);

        onlyNumberTextField();
    }

    protected void delayWindowClose(ActionEvent event) {
        PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
        delay.setOnFinished(e -> cancelWindow(event));
        delay.play();
    }

    @FXML
    protected void cancelWindow(ActionEvent event) {
        // clear cache img
        imgPreview.setImage(null);

        unbindFormatter();
        SceneController.close(event);
    }
}
