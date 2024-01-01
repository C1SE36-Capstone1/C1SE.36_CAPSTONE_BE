package com.example.capstone.service;

import com.example.capstone.dto.ProductCreateDTO;
import com.example.capstone.model.Product.Product;

public interface IProductService extends IService<Product>{
    String existsProductName(String product_name);
    Product findMaxCodeInDatabase();

    Product createProduct(ProductCreateDTO dto);
    void updateProduct(ProductCreateDTO productCreateDTO);

    boolean existsProductNameEdit(String productName, Long productId);
    Product findById(Long productId);
}
