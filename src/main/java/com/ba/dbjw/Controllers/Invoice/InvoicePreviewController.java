package com.ba.dbjw.Controllers.Invoice;

import com.ba.dbjw.Controllers.PopupWindow.NewWindowController;
import com.ba.dbjw.Entity.Invoice.Invoice;
import com.ba.dbjw.Entity.Invoice.InvoiceItem;
import com.ba.dbjw.Entity.Product.Product;
import com.ba.dbjw.Helpers.CurrentEntity.CurrentInvoice;
import com.ba.dbjw.Helpers.CurrentProduct;
import com.ba.dbjw.Repo.InvoiceItem.InvoiceItemRepo;
import com.ba.dbjw.Repo.InvoiceItem.InvoiceItemRepoImpl;
import com.ba.dbjw.Service.Invoice.InvoiceService;
import com.ba.dbjw.Service.Invoice.InvoiceServiceImpl;
import com.ba.dbjw.Service.Product.ProductService;
import com.ba.dbjw.Service.Product.ProductServiceImpl;
import com.ba.dbjw.Views.SceneController;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class InvoicePreviewController implements Initializable {
    @FXML
    private Text codeInvoice;

    @FXML
    private Text employeeName;

    @FXML
    private Text customerName;

    @FXML
    private TableView<InvoiceItem> productTable;

    @FXML
    private TableColumn<Product, String> codeProduct;

    @FXML
    private TableColumn<Product, String> nameProduct;

    @FXML
    private TableColumn<InvoiceItem, String> materialProduct;

    @FXML
    private TableColumn<InvoiceItem, String> sizeProduct;

    @FXML
    private TableColumn<InvoiceItem, Long> priceProduct;

    @FXML
    private TableColumn<InvoiceItem, Integer> quantityColumn;

    @FXML
    private TableColumn<InvoiceItem, BigDecimal> intoMoneyColumn;

    @FXML
    private Text totalMoney;

    @FXML
    private TextArea noteInvoice;

    @FXML
    private Text createAtIText;

    ObservableList<InvoiceItem> items = FXCollections.observableArrayList();

    private final InvoiceService<Invoice> invoiceService = new InvoiceServiceImpl();

    private final ProductService<Product> productService = new ProductServiceImpl();

    private final InvoiceItemRepo<InvoiceItem> invoiceItemRepo = new InvoiceItemRepoImpl();

    @FXML
    private void cancelView(ActionEvent event) {
        SceneController.close(event);
    }

    @FXML
    private void previewProduct(ActionEvent event) throws IOException {
        ObservableList<InvoiceItem> selectedRows = productTable.getSelectionModel().getSelectedItems();
        for (InvoiceItem e : selectedRows) {
            CurrentProduct.setCurrentProduct(e.getProduct());
            NewWindowController.getUpdateProductWindow();
        }
    }


    protected void addTableSettings() {
        productTable.setEditable(true);
        productTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        productTable.setItems(items);
    }

    private void setObList() {
        items.clear();
        items.addAll(invoiceItemRepo.getAllInvoiceItemByCode(CurrentInvoice.getCurrentInvoice()));
        for ( InvoiceItem item : items) {
            item.setProduct(productService.getProductByCode(item.getCode()));
        }
    }

    private void fillTable() {
        codeProduct.setCellValueFactory(new PropertyValueFactory<>("code"));
        nameProduct.setCellValueFactory(new PropertyValueFactory<>("name"));

        materialProduct.setCellValueFactory(cellData -> {
            InvoiceItem invoiceItem = cellData.getValue();
            String material = invoiceItem.getProduct().getMaterial();
            return new SimpleStringProperty(material);
        });

        sizeProduct.setCellValueFactory(cellData -> {
            InvoiceItem invoiceItem = cellData.getValue();
            String size = invoiceItem.getProduct().getSize();
            return new SimpleStringProperty(size);
        });

        priceProduct.setCellValueFactory(cellData -> {
            InvoiceItem invoiceItem = cellData.getValue();
            Long price = invoiceItem.getProduct().getPrice();
            return new SimpleLongProperty(price).asObject();
        });

        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        intoMoneyColumn.setCellValueFactory(cellData -> {
            InvoiceItem invoiceItem = cellData.getValue();
            Long price = invoiceItem.getProduct().getPrice();
            int quantity = invoiceItem.getQuantity();
            BigDecimal totalPrice = BigDecimal.valueOf(price).multiply(BigDecimal.valueOf(quantity));
            return new SimpleObjectProperty<>(totalPrice);
        });

        DecimalFormat format = new DecimalFormat("#,##0");
        intoMoneyColumn.setCellFactory(column -> new TableCell<InvoiceItem, BigDecimal>() {

            @Override
            protected void updateItem(BigDecimal amount, boolean empty) {
                super.updateItem(amount, empty);
                if (empty || amount == null) {
                    setText(null);
                } else {
                    setText(format.format(amount) + "VND");
                }
            }
        });

    }

    private void setTextValue() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        Invoice invoice = invoiceService.getInvoiceByCode(CurrentInvoice.getCurrentInvoice());
        codeInvoice.setText(invoice.getCode());
        noteInvoice.setText(invoice.getNote());
        String customerString = invoice.getCustomerName() != null ? invoice.getCustomerName() : "";
        customerName.setText("Khách hàng: " + customerString);
        employeeName.setText("Nhân viên: " + invoice.getEmployeeName());
        totalMoney.setText("Tổng tiền: " + invoice.getTotalMoney());
        createAtIText.setText("Ngày mua: " + invoice.getCreateAt().format(formatter));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTextValue();
        setObList();
        fillTable();
        addTableSettings();
    }
}
