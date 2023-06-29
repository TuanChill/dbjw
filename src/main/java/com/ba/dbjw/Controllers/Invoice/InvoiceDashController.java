package com.ba.dbjw.Controllers.Invoice;

import com.ba.dbjw.Controllers.DashController;
import com.ba.dbjw.Entity.Customer.Customer;
import com.ba.dbjw.Entity.Employee.Employee;
import com.ba.dbjw.Entity.Invoice.Invoice;
import com.ba.dbjw.Entity.Invoice.InvoiceItem;
import com.ba.dbjw.Entity.Product.Product;
import com.ba.dbjw.Service.Customer.CustomerService;
import com.ba.dbjw.Service.Customer.CustomerServiceImp;
import com.ba.dbjw.Service.Empoyee.EmployeeService;
import com.ba.dbjw.Service.Empoyee.EmployeeServiceImpl;
import com.ba.dbjw.Service.Invoice.InvoiceService;
import com.ba.dbjw.Service.Invoice.InvoiceServiceImpl;
import com.ba.dbjw.Service.Product.ProductService;
import com.ba.dbjw.Service.Product.ProductServiceImpl;


import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ResourceBundle;

import static com.ba.dbjw.Helpers.AlertPopup.*;
import static com.ba.dbjw.Helpers.LazyLoading.loadAndDisplayImage;

public class InvoiceDashController extends DashController implements Initializable {
    // cart
    @FXML
    protected TableView<InvoiceItem> cartTable;

    @FXML
    protected TableColumn<Product, String> codeColumn;

    @FXML
    protected TableColumn<Product, String> nameColumn;

    @FXML
    protected TableColumn<InvoiceItem, Integer> quantityColumn;

    @FXML
    protected TableColumn<InvoiceItem, BigDecimal> intoMoneyColumn;

    @FXML
    protected Text totalMoney;

    @FXML
    private TextArea noteInvoice;

    // product info
    @FXML
    private Text codeProduct;

    @FXML
    protected Text nameProduct;

    @FXML
    protected Text typeProduct;

    @FXML
    protected Text materialProduct;

    @FXML
    protected Text priceProduct;

    @FXML
    protected Text sizeProduct;

    @FXML
    protected TextArea descProduct;

    @FXML
    protected ImageView imgPreview;

    @FXML
    private TextField searchBar;

    ObservableList<InvoiceItem> items = FXCollections.observableArrayList();

    private Product selectedProduct;

    private final ProductService<Product> productService = new ProductServiceImpl();

    private final EmployeeService<Employee> employeeService = new EmployeeServiceImpl();

    private final CustomerService<Customer> customerService = new CustomerServiceImp();

    private final InvoiceService<Invoice> invoiceService = new InvoiceServiceImpl();

    @FXML
    protected void addItem(ActionEvent event) {
        if (!isHaveSelectedProduct()) {
            showAlert("Cảnh báo", "Chưa có sản phẩm nào được chọn!!", Alert.AlertType.WARNING);
            return;
        }
        String productCode = selectedProduct.getCode();
        boolean productExists = isProductExists(items, productCode);

        if (productExists) {
            // Product already exists in the items list
            handleIncreaseNumber(productCode);
        } else {
            // Add the new InvoiceItem to the list
            InvoiceItem newInvoiceItem = new InvoiceItem();
            newInvoiceItem.setProduct(selectedProduct);
            newInvoiceItem.setQuantity(1);
            items.add(newInvoiceItem);
        }
        updateTotalMoney();
        cartTable.refresh();
    }


    @FXML
    protected void paySubmit(ActionEvent event) {
        if (!items.isEmpty()) {
            String customerCode = dialogPay("Nhập mã khách hàng", false);
            String employeeCode = dialogPay("Nhập mã nhân viên", true);
            if (employeeCode != null) {
                Employee employee = employeeService.getEmployeeByCode(employeeCode);
                if (employee == null) {
                    showAlert("Lỗi", "Không tìm thấy nhân viên", Alert.AlertType.ERROR);
                } else {
                    Customer customer = null;
                    if (customerCode != null) {
                        customer = customerService.getCustomerByCode(customerCode);
                    }

                    Invoice newInvoice = Invoice.builder()
                            .items(items)
                            .customer(customer)
                            .customerName(customer != null ? customer.getName() : null)
                            .employee(employee)
                            .employeeName(employee.getName())
                            .note(noteInvoice.getText())
                            .build();
                    if (invoiceService.saveInvoice(newInvoice)) {
                        setDefaultValue();
                        items.clear();
                        cartTable.refresh();
                        showAlert("Thông báo", "Lưu hoá đơn thành công", Alert.AlertType.INFORMATION);
                    }
                }
            }
        } else {
            showAlert("Đã có lỗi xảy ra", "Giỏ hàng không được bỏ trống", Alert.AlertType.ERROR);
        }
    }

    @FXML
    protected void delItem(ActionEvent event) {
        ObservableList<InvoiceItem> selectedRows = cartTable.getSelectionModel().getSelectedItems();
        for (int i = selectedRows.size() - 1; i >= 0; i--) {
            InvoiceItem item = selectedRows.get(i);
            items.remove(item);
        }
        cartTable.refresh();
    }

    @FXML
    protected void increaseNumber(ActionEvent event) {
        ObservableList<InvoiceItem> selectedRows = cartTable.getSelectionModel().getSelectedItems();
        for (InvoiceItem e : selectedRows) {
            handleIncreaseNumber(e.getCode());
        }
        cartTable.refresh();
    }

    @FXML
    protected void decreaseNumber(ActionEvent event) {
        ObservableList<InvoiceItem> selectedRows = cartTable.getSelectionModel().getSelectedItems();
        for (int i = selectedRows.size() - 1; i >= 0; i--) {
            InvoiceItem item = selectedRows.get(i);
            handleDecreaseNumber(item.getCode());
        }
        cartTable.refresh();
    }


    @FXML
    private void searchBtn(ActionEvent event) {
        if (!searchBar.getText().trim().isEmpty()) {
            Product product = productService.getProductByCode(searchBar.getText().trim());
            setInfoProduct(product);
            selectedProduct = product;
        }
    }

    private boolean isHaveSelectedProduct() {
        return selectedProduct != null;
    }

    private void handleIncreaseNumber(String productCode) {
        for (InvoiceItem invoiceItem : items) {
            if (invoiceItem.getProduct().getCode().equals(productCode)) {
                int currentQuantity = invoiceItem.getQuantity();
                invoiceItem.setQuantity(currentQuantity + 1);
                updateTotalMoney();
                return;
            }
        }
    }

    private void handleDecreaseNumber(String productCode) {
        for (InvoiceItem invoiceItem : items) {
            if (invoiceItem.getProduct().getCode().equals(productCode)) {
                int currentQuantity = invoiceItem.getQuantity();
                if (currentQuantity > 1) {
                    invoiceItem.setQuantity(currentQuantity - 1);
                } else {
                    items.remove(invoiceItem);
                }
                updateTotalMoney();
                return;
            }
        }
    }

    private void updateTotalMoney() {
        int total = 0;
        for (InvoiceItem invoiceItem : items) {
            int quantity = invoiceItem.getQuantity();
            double price = invoiceItem.getProduct().getPrice();
            int intoMoney = (int) (price * quantity);
            total += intoMoney;
        }

        // Format the total money as Vietnamese Dong (VND) currency
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("#,### VND", symbols);
        String formattedTotal = decimalFormat.format(total);

        totalMoney.setText(formattedTotal);
    }


    // table setting property
    protected void addTableSettings() {
        cartTable.setEditable(true);
        cartTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        cartTable.setItems(items);
    }

    private void fillTable() {
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        intoMoneyColumn.setCellValueFactory(cellData -> {
            InvoiceItem invoiceItem = cellData.getValue();
            int quantity = invoiceItem.getQuantity();
            BigDecimal price = BigDecimal.valueOf(invoiceItem.getProduct().getPrice());
            BigDecimal intoMoney = price.multiply(BigDecimal.valueOf(quantity));
            return new SimpleObjectProperty<>(intoMoney);
        });
    }


    // set value for product screen
    private void setDefaultValue() {
        codeProduct.setText("Mã sản phẩm");
        nameProduct.setText("Tên sản phẩm");
        typeProduct.setText("Loại sản phẩm");
        materialProduct.setText("Chất liệu");
        priceProduct.setText("Giá bán");
        sizeProduct.setText("Kích cỡ");
        descProduct.setText("");
        imgPreview.setImage(null);
    }

    private void setInfoProduct(Product product) {
        codeProduct.setText("Mã: " + product.getCode());
        nameProduct.setText(product.getName());
        typeProduct.setText("Loại: " + product.getCategory());
        materialProduct.setText("Chất liệu: " + product.getMaterial());
        priceProduct.setText("Giá bán: " + product.getPrice());
        sizeProduct.setText("Kích cỡ: " + product.getSize());
        descProduct.setText(product.getDescription());
        loadAndDisplayImage(imgPreview, product.getImgUrl());
        imgPreview.setFitWidth(350);
        imgPreview.setFitHeight(333);
    }

    private boolean isProductExists(ObservableList<InvoiceItem> items, String productCode) {
        for (InvoiceItem invoiceItem : items) {
            if (invoiceItem.getProduct().getCode().equals(productCode)) {
                return true;
            }
        }
        return false;
    }

    private void preventEnterInSearchBar() {
        searchBar.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                event.consume();
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        decentralization();
        preventEnter();
        preventEnterInSearchBar();
        setUserInfo();
        setDefaultValue();
        addTableSettings();
        fillTable();
    }

}
