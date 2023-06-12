package com.ba.dbjw.Controllers.Admin;



import com.ba.dbjw.Controllers.PopupWindowControllers.NewWindowController;
import com.ba.dbjw.Entity.Product.Product;
import com.ba.dbjw.Helpers.CurrentTime;
import com.ba.dbjw.Helpers.CurrentUser;
import com.ba.dbjw.Helpers.SceneName;
import com.ba.dbjw.Helpers.UpdateStatus;
import com.ba.dbjw.Service.Product.ProductServiceImpl;
import com.ba.dbjw.Views.SceneController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.IOException;
import java.time.LocalDate;

public class ProductDashController {

    @FXML
    private Label title;

    @FXML
    private Label date;

    @FXML
    private Label stats;

    @FXML
    private Label updateTime;

    @FXML
    private Button customerBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private Label userInfo;

    @FXML
    private TextField searchBar;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, String> codeColumn;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TableColumn<Product, Long> priceColumn;

    @FXML
    private TableColumn<Product, String> materialColumn;

    @FXML
    private TableColumn<Product, String> descColumn;

    @FXML
    private TableColumn<Product, String> imgColumn;

    ProductServiceImpl productService = new ProductServiceImpl();
    ObservableList<Product> productsObList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        setTexts();
        setObList();
        fillTable();
        addTableSettings();
        exitBtn.setOnAction(SceneController::close);
    }

    private void setTexts() {
        title.setText(SceneName.PRODUCT.getName());
        date.setText(LocalDate.now().toString());
        updateTime.setText("Last update: " + CurrentTime.getTime());
        setDbInfo();
        setUserInfo();
    }

    private void setObList() {
        productsObList.clear();
        productsObList.addAll(productService.getAllProducts());
    }

    private void fillTable() {
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        materialColumn.setCellValueFactory(new PropertyValueFactory<>("material"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        imgColumn.setCellFactory(col -> new TableCell<Product, String>() {
            private void updateItem(Boolean item, boolean empty) {
                super.updateItem(String.valueOf(item), empty);
                setText(empty ? null : item ? "Xem ảnh" : "Không có ảnh");
            }
        });
    }

    private void addTableSettings() {
        productTable.setEditable(true);
        productTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        productTable.setItems(getSortedList());
    }

    private SortedList<Product> getSortedList() {
        SortedList<Product> sortedList = new SortedList<>(getFilteredList());
        sortedList.comparatorProperty().bind(productTable.comparatorProperty());
        return sortedList;
    }

    private FilteredList<Product> getFilteredList() {
        FilteredList<Product> filteredList = new FilteredList<>(productsObList, b -> true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) ->
                filteredList.setPredicate(product -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (product.getCode().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (product.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (product.getMaterial().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (product.getSize().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                }));
        return filteredList;
    }

    @FXML
    private void changeDescCell(TableColumn.CellEditEvent<Product, String> editEvent) {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        selectedProduct.setDescription(editEvent.getNewValue());
        productService.updateProduct(selectedProduct);
    }

    @FXML
    private void changePriceCell(TableColumn.CellEditEvent<Product, String> editEvent) {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        selectedProduct.setPrice(Long.valueOf(editEvent.getNewValue()));
        productService.updateProduct(selectedProduct);
    }

    @FXML
    private void changeMaterialCell(TableColumn.CellEditEvent<Product, String> editEvent) {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        selectedProduct.setMaterial(editEvent.getNewValue());
        productService.updateProduct(selectedProduct);
    }
    @FXML
    private void deletePets(ActionEvent event) throws Exception {
        ObservableList<Product> selectedRows = productTable.getSelectionModel().getSelectedItems();
        for (Product product : selectedRows) {
            productService.deleteProduct(product);
        }
        refreshScreen(event);
    }

    @FXML
    private void newWindow(ActionEvent event) throws IOException {
        NewWindowController.getNewProductWindow();
        if(UpdateStatus.isProductAdded()) {
            refreshScreen(event);
            UpdateStatus.setIsProductAdded(false);
        }
    }

    private void setUserInfo() {
        userInfo.setText(String.format("User: %s", CurrentUser.getCurrentUser().getUserName()));
    }

    private void setDbInfo() {
        stats.setText(String.format("Total products in database: %s", productService.getNumberOfProduct()));
    }

    @FXML
    private void refreshScreen(ActionEvent event) throws IOException {
        SceneController.getProductDashScene(event);
    }

    @FXML
    private void showVisitScreen(ActionEvent event) throws IOException {
//        SceneController.getVisitScene(event);
    }

    @FXML
    private void showVetScreen(ActionEvent event) throws IOException {
//        SceneController.getVetsScene(event);
    }
}