package com.ba.dbjw.Controllers.Product;

import com.ba.dbjw.Helpers.BindingInput;
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

    protected final String[] typeProductList = {"Nhẫn", "Bông tai ", "Dây chuyền"};

    protected final String[] materialList = {"Bạc", "Vàng", "Kim Cương"};

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

    protected void delayWindowClose(ActionEvent event) {
        PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
        delay.setOnFinished(e -> cancelWindow(event));
        delay.play();
    }

    @FXML
    protected void cancelWindow(ActionEvent event) {
        unbindFormatter();
        SceneController.close(event);
    }
}
