package com.ba.dbjw.Helpers;


import com.ba.dbjw.Entity.Product.Product;
public class CurrentProduct {

    private static Product product;

    private CurrentProduct() {
    }

    public static Product getCurrentProduct() {
        return product;
    }

    public static void setCurrentProduct(Product currentProduct) {
        product = currentProduct;
    }
}

