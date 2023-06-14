package com.ba.dbjw.Helpers;

public enum ScenePath {
    LOGIN("/Fxml/login.fxml"),
    ADMIN("/Fxml/Admin/admin.fxml"),
    PRODUCTDASH("/Fxml/Admin/productDash.fxml"),
    ADDPRODUCT("/Fxml/Admin/addProduct.fxml"),
    UPDATEPRODUCT("/Fxml/Admin/updateProduct.fxml")
    ;

    private final String path;

    private ScenePath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
