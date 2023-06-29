package com.ba.dbjw.Controllers.Invoice;

import com.ba.dbjw.Controllers.DashController;
import com.ba.dbjw.Controllers.PopupWindow.NewWindowController;
import com.ba.dbjw.Entity.Invoice.Invoice;
import com.ba.dbjw.Helpers.CurrentEntity.CurrentInvoice;
import com.ba.dbjw.Service.Invoice.InvoiceService;
import com.ba.dbjw.Service.Invoice.InvoiceServiceImpl;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;

public class InvoiceHistoryController extends DashController {
    @FXML
    private DatePicker dateFrom;

    @FXML
    private DatePicker dateTo;

    @FXML
    private Button clearBtn;

    @FXML
    private TableView<Invoice> invoiceTable;

    @FXML
    private TableColumn<Invoice, String> codeColumn;

    @FXML
    private TableColumn<Invoice, String> nameEmployee;

    @FXML
    private TableColumn<Invoice, String> nameCustomer;

    @FXML
    private TableColumn<Invoice, LocalDateTime> createAtColumn;

    @FXML
    private TableColumn<Invoice, BigDecimal> totalMoneyColumn;

    @FXML
    private TableColumn<Invoice, String> noteColumn;

    ObservableList<Invoice> invoiceObList = FXCollections.observableArrayList();

    private final InvoiceService<Invoice> invoiceService = new InvoiceServiceImpl();

    @FXML
    private void previewInvoice(ActionEvent event) throws IOException {
        ObservableList<Invoice> selectedRows = invoiceTable.getSelectionModel().getSelectedItems();
        for (Invoice e : selectedRows) {
            CurrentInvoice.setCurrentInvoice(e.getCode());
            NewWindowController.getInvoiceWindow();
        }
    }

    @FXML
    private void initialize() {
        preventEnter();
        setObList();
        fillTable();
        addTableSettings();
        clearSearchResults();
    }

    private void setObList() {
        invoiceObList.clear();
        invoiceObList.addAll(invoiceService.getAllInvoices());
    }

    private void fillTable() {
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        nameEmployee.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        nameCustomer.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        createAtColumn.setCellValueFactory(new PropertyValueFactory<>("createAt"));
        totalMoneyColumn.setCellValueFactory(new PropertyValueFactory<>("totalMoney"));
        noteColumn.setCellValueFactory(new PropertyValueFactory<>("note"));

        // Configure the totalMoney column
        DecimalFormat format = new DecimalFormat("#,##0");
        totalMoneyColumn.setCellFactory(column -> new TableCell<Invoice, BigDecimal>() {
            @Override
            protected void updateItem(BigDecimal amount, boolean empty) {
                super.updateItem(amount, empty);
                if (empty || amount == null) {
                    setText(null);
                } else {
                    setText(format.format(amount) + " VND");
                }
            }
        });

        // Configure the createAt column
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        createAtColumn.setCellFactory(column -> new TableCell<Invoice, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime dateTime, boolean empty) {
                super.updateItem(dateTime, empty);
                if (empty || dateTime == null) {
                    setText(null);
                } else {
                    setText(dateTime.format(formatter));
                }
            }
        });
    }

    private void addTableSettings() {
        invoiceTable.setEditable(true);
        invoiceTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        invoiceTable.setItems(getSortedList());
    }

    private SortedList<Invoice> getSortedList() {
        SortedList<Invoice> sortedList = new SortedList<>(getFilteredListByDates());
        sortedList.comparatorProperty().bind(invoiceTable.comparatorProperty());
        return sortedList;
    }

    @FXML
    private FilteredList<Invoice> getFilteredListByDates() {
        FilteredList<Invoice> filteredItems = new FilteredList<>(getFilteredListByString());

        filteredItems.predicateProperty().bind(Bindings.createObjectBinding(() -> {
                    LocalDate minDate = dateFrom.getValue();
                    LocalDate maxDate = dateTo.getValue();

                    final LocalDate finalMin = minDate == null ? LocalDate.MIN : minDate;
                    final LocalDate finalMax = maxDate == null ? LocalDate.MAX : maxDate;

                    return ti -> !finalMin.isAfter(ChronoLocalDate.from(ti.getCreateAt())) && !finalMax.isBefore(ChronoLocalDate.from(ti.getCreateAt()));
                },
                dateFrom.valueProperty(),
                dateTo.valueProperty()));
        return filteredItems;
    }

    private FilteredList<Invoice> getFilteredListByString() {
        FilteredList<Invoice> filteredList = new FilteredList<>(invoiceObList, b -> true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) ->
                filteredList.setPredicate(invoice -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (invoice.getCode().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (invoice.getTotalMoney().toString().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (invoice.getCustomerName() != null && invoice.getCustomerName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (invoice.getEmployeeName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else return invoice.getNote().toLowerCase().contains(lowerCaseFilter);
                }));
        return filteredList;
    }

    @FXML
    private void clearSearchResults() {
        clearBtn.setOnAction(event -> {
            dateFrom.setValue(null);
            dateTo.setValue(null);
            searchBar.setText("");
            invoiceTable.setItems(getSortedList());
        });
    }
}
