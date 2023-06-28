package com.ba.dbjw.Controllers.Product;


import com.ba.dbjw.Controllers.DashController;
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

public class ProductDashController extends DashController {
    @FXML
    protected TableView<Product> productTable;

    @FXML
    protected TableColumn<Product, String> codeColumn;

    @FXML
    protected TableColumn<Product, String> nameColumn;

    @FXML
    protected TableColumn<Product, String> priceColumn;

    @FXML
    protected TableColumn<Product, String> sizeColumn;

    @FXML
    protected TableColumn<Product, String> stockColumn;

    @FXML
    protected TableColumn<Product, String> materialColumn;

    @FXML
    protected TableColumn<Product, String> descColumn;

    @FXML
    protected TableColumn<Product, String> imgColumn;

    ProductServiceImpl productService = new ProductServiceImpl();
    ObservableList<Product> productsObList = FXCollections.observableArrayList();

    @FXML
    protected void initialize() {
        preventEnter();
        setTexts();
        setObList();
        fillTable();
        addTableSettings();
    }

    protected void setTexts() {
        title.setText(SceneName.PRODUCT.getName());
        date.setText(LocalDate.now().toString());
        updateTime.setText("Cập nhật cuối cùng: " + CurrentTime.getTime());
        setDbInfo();
        setUserInfo();
    }

    protected void setObList() {
        productsObList.clear();
        productsObList.addAll(productService.getAllProducts());
    }

    protected void fillTable() {
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

    protected void addTableSettings() {
        productTable.setEditable(true);
        productTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        productTable.setItems(getSortedList());
    }

    protected SortedList<Product> getSortedList() {
        SortedList<Product> sortedList = new SortedList<>(getFilteredList());
        sortedList.comparatorProperty().bind(productTable.comparatorProperty());
        return sortedList;
    }

    protected FilteredList<Product> getFilteredList() {
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
                    } else return product.getSize().toLowerCase().contains(lowerCaseFilter);
                }));
        return filteredList;
    }

    @FXML
    protected void changeDescCell(TableColumn.CellEditEvent<Product, String> editEvent) {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        selectedProduct.setDescription(editEvent.getNewValue());
        productService.updateProduct(selectedProduct);
    }

    @FXML
    protected void changeSizeCell(TableColumn.CellEditEvent<Product, String> editEvent) {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        selectedProduct.setSize(editEvent.getNewValue());
        productService.updateProduct(selectedProduct);
    }
    @FXML
    protected void deleteProduct(ActionEvent event) throws Exception {
        ObservableList<Product> selectedRows = productTable.getSelectionModel().getSelectedItems();
        for (Product product : selectedRows) {
            productService.deleteProduct(product);
        }
        refreshScreen(event);
    }

    @FXML
    protected void updateProduct(ActionEvent event) throws IOException {
        ObservableList<Product> selectedRows = productTable.getSelectionModel().getSelectedItems();
        for (Product product : selectedRows) {
            CurrentProduct.setCurrentProduct(product);
            NewWindowController.getUpdateProductWindow();
        }
        refreshScreen(event);
    }

    protected void setDbInfo() {
        stats.setText(String.format("Số lượng sản phẩm trong cơ sở dữ liệu: %s", productService.getNumberOfProduct()));
    }
}