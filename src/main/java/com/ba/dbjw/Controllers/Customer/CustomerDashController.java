package com.ba.dbjw.Controllers.Customer;


import com.ba.dbjw.Controllers.PopupWindow.NewWindowController;
import com.ba.dbjw.Entity.Customer.Customer;
import com.ba.dbjw.Helpers.*;
import com.ba.dbjw.Helpers.CurrentEntity.CurrentUser;
import com.ba.dbjw.Helpers.UpdateStatus.UpdateStatusCustomer;
import com.ba.dbjw.Service.Customer.CustomerServiceImp;
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

public class CustomerDashController {

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
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, String> codeColumn;

    @FXML
    private TableColumn<Customer, String> nameColumn;

    @FXML
    private TableColumn<Customer, String> genderColumn;

    @FXML
    private TableColumn<Customer, String> phoneColumn;

    @FXML
    private TableColumn<Customer, String> addressColumn;

    CustomerServiceImp customerService = new CustomerServiceImp();
    ObservableList<Customer> customersObList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        setTexts();
        setObList();
        fillTable();
        addTableSettings();
        exitBtn.setOnAction(SceneController::close);
    }

    private void setTexts() {
        title.setText(SceneName.CUSTOMER.getName());
        date.setText(LocalDate.now().toString());
        updateTime.setText("Cập nhật cuối cùng: " + CurrentTime.getTime());
        setDbInfo();
        setUserInfo();
    }

    private void setObList() {
        customersObList.clear();
        customersObList.addAll(customerService.getAllCustomer());
    }

    private void fillTable() {
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    private void addTableSettings() {
        customerTable.setEditable(true);
        customerTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        customerTable.setItems(getSortedList());
    }

    private SortedList<Customer> getSortedList() {
        SortedList<Customer> sortedList = new SortedList<>(getFilteredList());
        sortedList.comparatorProperty().bind(customerTable.comparatorProperty());
        return sortedList;
    }

    private FilteredList<Customer> getFilteredList() {
        FilteredList<Customer> filteredList = new FilteredList<>(customersObList, b -> true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) ->
                filteredList.setPredicate(customer -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (customer.getCode().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (customer.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }else if (customer.getPhoneNumber().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }else if (customer.getAddress().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else {
                        return false;
                    }
                }));
        return filteredList;
    }

    @FXML
    private void changeAddressCell(TableColumn.CellEditEvent<Customer, String> editEvent) {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        selectedCustomer.setAddress(editEvent.getNewValue());
        customerService.updateCustomer(selectedCustomer);
    }

    @FXML
    private void changeNameCell(TableColumn.CellEditEvent<Customer, String> editEvent) {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        selectedCustomer.setName(editEvent.getNewValue());
        customerService.updateCustomer(selectedCustomer);
    }
    @FXML
    private void deleteCustomer(ActionEvent event) throws Exception {
        ObservableList<Customer> selectedRows = customerTable.getSelectionModel().getSelectedItems();
        for (Customer customer : selectedRows) {
            customerService.delCustomer(customer);
        }
        refreshScreen(event);
    }

    @FXML
    private void updateCustomer(ActionEvent event) throws IOException {
        ObservableList<Customer> selectedRows = customerTable.getSelectionModel().getSelectedItems();
        for (Customer customer : selectedRows) {
            CurrentCustomer.setCurrentCustomer(customer);
            NewWindowController.getUpdateCustomerWindow();
        }
        refreshScreen(event);
    }

    @FXML
    private void newWindow(ActionEvent event) throws IOException {
        NewWindowController.getNewCustomerWindow();
        if (UpdateStatusCustomer.isCustomerAdded()) {
            refreshScreen(event);
            UpdateStatusCustomer.setIsCustomerAdded(false);
        }
    }

    private void setUserInfo() {
        userInfo.setText(String.format("Người dùng: %s", CurrentUser.getCurrentUser().getUserName()));
    }

    private void setDbInfo() {
        stats.setText(String.format("Số lượng khách hàng trong cơ sở dữ liệu: %s", customerService.getNumberOfCustomer()));
    }

    @FXML
    private void refreshScreen(ActionEvent event) throws IOException {
        SceneController.getCustomerDashScene(event);
    }

    @FXML
    private void showProductScreen(ActionEvent event) throws IOException {
        SceneController.getProductDashScene(event);
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