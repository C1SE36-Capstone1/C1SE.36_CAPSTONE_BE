package com.example.capstone.service.Impl;

import com.example.capstone.Error.NotFoundById;
import com.example.capstone.dto.ProductCreateDTO;
import com.example.capstone.model.Product.Category;
import com.example.capstone.model.Product.Product;
import com.example.capstone.repository.Product.IProductRepository;
import com.example.capstone.service.IProductService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Override
    public String existsProductName(String product_name) {
        return productRepository.existsProductName(product_name);
    }
    @Override
    public Product findMaxCodeInDatabase() {
        return productRepository.findMaxCodeInDatabase();
    }
    @Override
    public Page<Product> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product update(Product product) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Async
    @Override
    @Transactional
    public Product createProduct(ProductCreateDTO dto) {
        LocalDate date = LocalDate.now();
        Category category = categoryService.findByCategoryName(dto.getCategory());
        if (category == null) {
            throw new EntityNotFoundException("Category with name " + dto.getCategory() + " was not found");
        }

        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setEnteredDate(date);
        product.setDescription(dto.getDescription());
        product.setImage(dto.getImage());
        product.setExpireDate(dto.getExpireDate());
        product.setCode(dto.getCode());
        product.setDiscount(0);
        product.setCategory(category);
        product.setSold(0);
        product.setStatus(true); // Giả sử sản phẩm luôn được đặt là 'true' khi tạo mới

        return productRepository.save(product);
    }

    @Override
    @Transactional
    public void updateProduct(ProductCreateDTO productCreateDTO) {
        // Chuyển đổi tên danh mục thành categoryId.
        Category category = categoryService.findByCategoryName(productCreateDTO.getCategory());
        if (category == null) {
            throw new EntityNotFoundException("Category with name " + productCreateDTO.getCategory() + " was not found");
        }

        productRepository.updateProduct(
                productCreateDTO.getExpireDate(),
                productCreateDTO.isStatus(),
                productCreateDTO.getImage(),
                productCreateDTO.getName(),
                productCreateDTO.getDescription(),
                productCreateDTO.getPrice(),
                productCreateDTO.getQuantity(),
                category.getCategoryId(),
                productCreateDTO.getProductId()
        );
    }


    @Override
    public boolean existsProductNameEdit(String productName, Long productId) {
        List<Product> products = productRepository.findByName(productName);
        for (Product product : products) {
            if (!product.getProductId().equals(productId)) {
                return true;
            }
        }
        return false;
    }


}
