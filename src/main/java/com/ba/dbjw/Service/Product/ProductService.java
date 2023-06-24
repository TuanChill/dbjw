package com.ba.dbjw.Service.Product;

import java.util.List;

public interface ProductService<T> {
    boolean createProduct(T product);
    List<T> getAllProducts();
    void deleteProduct(T product);
    boolean updateProduct(T product);
    Long getNumberOfProduct();
}
