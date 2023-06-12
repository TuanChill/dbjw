package com.ba.dbjw.Repo.Product;


import java.util.List;

public interface ProductRepo<T> {
    List<T> getProductsByName(String productName);
    T getProductById(Long id);
    T getProductByCode(String code);
    Long getNumberOfProducts();
    List<T> getProducts();
    void saveProduct(T product);
    void updateProduct(T product);
    void delProduct(T product);
}
