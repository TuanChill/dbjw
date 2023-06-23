package com.ba.dbjw.Controllers.Product;


import com.ba.dbjw.Controllers.PopupWindow.NewWindowController;
import com.ba.dbjw.Entity.Product.Product;
import com.ba.dbjw.Helpers.*;
import com.ba.dbjw.Helpers.CurrentEntity.CurrentUser;
import com.ba.dbjw.Helpers.UpdateStatus.UpdateStatusProduct;
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
    private TableColumn<Product, String> priceColumn;

    @FXML
    private TableColumn<Product, String> sizeColumn;

    @FXML
    private TableColumn<Product, String> stockColumn;

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
        updateTime.setText("Cập nhật cuối cùng: " + CurrentTime.getTime());
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
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
        materialColumn.setCellValueFactory(new PropertyValueFactory<>("material"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        imgColumn.setCellValueFactory(new PropertyValueFactory<>("imgUrl"));
        sizeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descColumn.setCellFactory(TextFieldTableCell.forTableColumn());
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
                    } else {
                        return false;
                    }
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
    private void changeSizeCell(TableColumn.CellEditEvent<Product, String> editEvent) {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        selectedProduct.setSize(editEvent.getNewValue());
        productService.updateProduct(selectedProduct);
    }
    @FXML
    private void deleteProduct(ActionEvent event) throws Exception {
        ObservableList<Product> selectedRows = productTable.getSelectionModel().getSelectedItems();
        for (Product product : selectedRows) {
            productService.deleteProduct(product);
        }
        refreshScreen(event);
    }

    @FXML
    private void updateProduct(ActionEvent event) throws IOException {
        ObservableList<Product> selectedRows = productTable.getSelectionModel().getSelectedItems();
        for (Product product : selectedRows) {
            CurrentProduct.setCurrentProduct(product);
            NewWindowController.getUpdateProductWindow();
        }
        refreshScreen(event);
    }

    @FXML
    private void newWindow(ActionEvent event) throws IOException {
        NewWindowController.getNewProductWindow();
        if (UpdateStatusProduct.isProductAdded()) {
            refreshScreen(event);
            UpdateStatusProduct.setIsProductAdded(false);
        }
    }

    private void setUserInfo() {
        userInfo.setText(String.format("Người dùng: %s", CurrentUser.getCurrentUser().getUserName()));
    }

    private void setDbInfo() {
        stats.setText(String.format("Số lượng sản phẩm trong cơ sở dữ liệu: %s", productService.getNumberOfProduct()));
    }

    @FXML
    private void refreshScreen(ActionEvent event) throws IOException {
        SceneController.getProductDashScene(event);
    }

    @FXML
    private void showCustomerScreen(ActionEvent event) throws IOException {
        SceneController.getCustomerDashScene(event);
    }

    @FXML
    private void showInvoiceScreen(ActionEvent event) throws  IOException {
        SceneController.getInvoiceDashScene(event);
    }

    @FXML
    private void showEmployeeScreen(ActionEvent event) throws IOException {
        SceneController.getEmployeeDashScene(event);
    }

}