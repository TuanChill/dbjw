package com.ba.dbjw.Helpers;

public enum ScenePath {
    LOGIN("/Fxml/login.fxml"),
    ADMIN("/Fxml/Product/admin.fxml"),
    PRODUCTDASH("/Fxml/Product/productDash.fxml"),
    ADDPRODUCT("/Fxml/Product/addProduct.fxml"),
    UPDATEPRODUCT("/Fxml/Product/updateProduct.fxml"),

    CUSTOMERDASH("/Fxml/Customer/customerDash.fxml"),
    ADDCUSTOMER("/Fxml/Customer/addCustomer.fxml"),
    UPDATECUSTOMER("/Fxml/Customer/updateCustomer.fxml"),


    EMPLOYEEDASH("/Fxml/Employee/employeeDash.fxml"),
    ADDEMPLOYEE("/Fxml/Employee/addEmployee.fxml"),
    UPDATEEMPLOYEE("/Fxml/Employee/updateEmployee.fxml"),

    INVOICEDASH("/Fxml/Invoice/invoiceDash.fxml"),
    INVOICEHISTORY("/Fxml/Invoice/invoiceHistory.fxml"),
    ;

    private final String path;

    private ScenePath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
