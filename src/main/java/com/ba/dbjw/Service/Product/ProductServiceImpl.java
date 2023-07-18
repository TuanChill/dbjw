package com.ba.dbjw.Service.Product;


import com.ba.dbjw.Entity.Product.Product;
import com.ba.dbjw.Repo.Product.ProductRepo;
import com.ba.dbjw.Repo.Product.ProductRepoImpl;
import com.ba.dbjw.Utils.CloudinaryUtil;

import java.util.List;

public class ProductServiceImpl implements ProductService<Product> {
    private final ProductRepo<Product> productRepo = new ProductRepoImpl();

    @Override
    public Product getProductByCode(String code) {
        if (code != null && !code.isEmpty()) {
            return productRepo.getProductByCode(code);
        }
        return null;
    }

    @Override
    public boolean createProduct(Product product) {
        String imgPath = product.getImgUrl();
        if (imgPath != null) {
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
    public boolean deleteProduct(Product product) {
        try {
            return productRepo.delProduct(product);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateProduct(Product product) {
        String imgPath = product.getImgUrl();
        if (imgPath != null && !imgPath.contains("res.cloudinary.com")) {
            String url = CloudinaryUtil.uploadImgToCloudinary(imgPath);
            product.setImgUrl(url);
        }
        return productRepo.updateProduct(product);
    }

    @Override
    public Long getNumberOfProduct() {
        return productRepo.getNumberOfProducts();
    }

    @Override
    public void decreaseStockProduct(Product product, int value) {
        int currStock = product.getStock();
        if (currStock > 0) {
            product.setStock(currStock - value);
            productRepo.updateProduct(product);
        }
    }
}
