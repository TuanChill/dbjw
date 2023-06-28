package com.ba.dbjw.Service.Product;

import java.util.List;

public interface ProductService<T> {
    T getProductByCode(String code);
    boolean createProduct(T product);
    List<T> getAllProducts();
    void deleteProduct(T product);
    boolean updateProduct(T product);
    Long getNumberOfProduct();
}
