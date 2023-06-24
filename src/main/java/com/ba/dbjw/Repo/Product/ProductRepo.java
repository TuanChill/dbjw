package com.ba.dbjw.Repo.Product;


import java.util.List;

public interface ProductRepo<T> {
    List<T> getProductsByName(String productName);
    T getProductByCode(String code);
    Long getNumberOfProducts();
    List<T> getProducts();
    boolean saveProduct(T product);
    boolean updateProduct(T product);
    void delProduct(T product);
}
