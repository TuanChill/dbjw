package com.ba.dbjw.Service.Product;

import java.util.List;

public interface ProductService<T> {
    void createProduct(T product);
    List<T> getAllProducts();
    void deleteProduct(T product);
    void updateProduct(T product);
    Long getNumberOfProduct();
    Boolean checkProductIsExist(String code);
}
