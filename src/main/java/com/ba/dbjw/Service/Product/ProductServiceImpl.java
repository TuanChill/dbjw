package com.ba.dbjw.Service.Product;


import com.ba.dbjw.Entity.Product.Product;
import com.ba.dbjw.Repo.Product.ProductRepo;
import com.ba.dbjw.Repo.Product.ProductRepoImpl;

import java.util.List;

public class ProductServiceImpl implements ProductService<Product> {
    private final ProductRepo<Product> productRepo = new ProductRepoImpl();

    @Override
    public void createProduct(Product data) {
            productRepo.saveProduct(data);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.getProducts();
    }

    @Override
    public void deleteProduct(Product product) {
        productRepo.delProduct(product);
    }

    @Override
    public void updateProduct(Product product) {
        productRepo.updateProduct(product);
    }

    @Override
    public Long getNumberOfProduct() {
        return productRepo.getNumberOfProducts();
    }
}
