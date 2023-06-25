package com.ba.dbjw.Controllers.Employee;


import com.ba.dbjw.Controllers.DashController;
import com.ba.dbjw.Controllers.PopupWindow.NewWindowController;
import com.ba.dbjw.Entity.Employee.Employee;
import com.ba.dbjw.Helpers.CurrentEntity.CurrentEmployee;
import com.ba.dbjw.Helpers.CurrentEntity.CurrentUser;
import com.ba.dbjw.Helpers.CurrentTime;
import com.ba.dbjw.Helpers.SceneName;
import com.ba.dbjw.Helpers.UpdateStatus.UpdateStatusEmployee;
import com.ba.dbjw.Service.Empoyee.EmployeeServiceImpl;
import com.ba.dbjw.Views.SceneController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDate;

public class EmployeeDashController extends DashController {

    @FXML
    protected TableView<Employee> employeeTable;

    @FXML
    protected TableColumn<Employee, String> codeColumn;

    @FXML
    protected TableColumn<Employee, String> nameColumn;

    @FXML
    protected TableColumn<Employee, String> genderColumn;

    @FXML
    protected TableColumn<Employee, String> phoneNumberColumn;

    @FXML
    protected TableColumn<Employee, String> cccdColumn;

    @FXML
    protected TableColumn<Employee, String> addressColumn;

    @FXML
    protected TableColumn<Employee, String> positionColumn;

    EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
    ObservableList<Employee> employeesObList = FXCollections.observableArrayList();

    @FXML
    protected void initialize() {
        setTexts();
        setObList();
        fillTable();
        addTableSettings();
    }


    protected void setTexts() {
        title.setText(SceneName.EMPLOYEE.getName());
        date.setText(LocalDate.now().toString());
        updateTime.setText("Cập nhật cuối cùng: " + CurrentTime.getTime());
        setDbInfo();
        setUserInfo();
    }


    protected void setObList() {
        employeesObList.clear();
        employeesObList.addAll(employeeService.getAllEmployees());
    }


    protected void fillTable() {
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        cccdColumn.setCellValueFactory(new PropertyValueFactory<>("cccd"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
    }


    protected void addTableSettings() {
        employeeTable.setEditable(true);
        employeeTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        employeeTable.setItems(getSortedList());
    }


    protected SortedList<Employee> getSortedList() {
        SortedList<Employee> sortedList = new SortedList<>(getFilteredList());
        sortedList.comparatorProperty().bind(employeeTable.comparatorProperty());
        return sortedList;
    }


    protected FilteredList<Employee> getFilteredList() {
        FilteredList<Employee> filteredList = new FilteredList<>(employeesObList, b -> true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) ->
                filteredList.setPredicate(employee -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (employee.getCode().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (employee.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (employee.getPosition().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (employee.getPhoneNumber().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    else if (employee.getAddress().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }else return employee.getGender().toLowerCase().contains(lowerCaseFilter);
                }));
        return filteredList;
    }

    @FXML
    protected void changeAddressCell(TableColumn.CellEditEvent<Employee, String> editEvent) {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        selectedEmployee.setAddress(editEvent.getNewValue());
        employeeService.updateEmployee(selectedEmployee);
    }

    @FXML
    protected void changeNameCell(TableColumn.CellEditEvent<Employee, String> editEvent) {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        selectedEmployee.setName(editEvent.getNewValue());
        employeeService.updateEmployee(selectedEmployee);
    }
    @FXML
    protected void deleteEmployee(ActionEvent event) throws Exception {
        ObservableList<Employee> selectedRows = employeeTable.getSelectionModel().getSelectedItems();
        for (Employee employee : selectedRows) {
            employeeService.deleteEmployee(employee);
        }
        refreshScreen(event);
    }

    @FXML
    protected void updateEmployee(ActionEvent event) throws IOException {
        ObservableList<Employee> selectedRows = employeeTable.getSelectionModel().getSelectedItems();
        for (Employee employee : selectedRows) {
            CurrentEmployee.setCurrentEmployee(employee);
            NewWindowController.getUpdateProductWindow();
        }
        refreshScreen(event);
    }

    @FXML
    protected void newWindow(ActionEvent event) throws IOException {
        NewWindowController.getNewEmployeeWindow();
        if (UpdateStatusEmployee.isEmployeeAdded()) {
            refreshScreen(event);
            UpdateStatusEmployee.setIsEmployeeAdded(false);
        }
    }

    protected void setDbInfo() {
        stats.setText(String.format("Số lượng nhân viên trong cơ sở dữ liệu: %s", employeeService.getNumberOfEmployee()));
    }

    @FXML
    protected void refreshScreen(ActionEvent event) throws IOException {
        SceneController.getEmployeeDashScene(event);
    }
}