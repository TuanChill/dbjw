package com.ba.dbjw.Service.Product;


import com.ba.dbjw.Entity.Product.Product;
import com.ba.dbjw.Repo.Product.ProductRepo;
import com.ba.dbjw.Repo.Product.ProductRepoImpl;
import com.ba.dbjw.Utils.CloudinaryUtil;

import java.util.List;

public class ProductServiceImpl implements ProductService<Product> {
    private final ProductRepo<Product> productRepo = new ProductRepoImpl();

    @Override
    public boolean createProduct(Product product) {
        String imgPath = product.getImgUrl();
        if(imgPath != null) {
            String url = CloudinaryUtil.uploadImgToCloudinary(imgPath);
            product.setImgUrl(url);
        }
          return productRepo.saveProduct(product);
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
    public boolean updateProduct(Product product) {
        return productRepo.updateProduct(product);
    }

    @Override
    public Long getNumberOfProduct() {
        return productRepo.getNumberOfProducts();
    }
}
