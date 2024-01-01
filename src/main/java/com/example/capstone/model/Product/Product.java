package com.example.capstone.model.Product;
import com.example.capstone.dto.ProductCreateDTO;
import com.example.capstone.model.User.User;
import com.example.capstone.service.Impl.CategoryService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String code;
    private String name;
    private Integer quantity;
    private Double price;
    private Integer discount;
    private String image;
    private String description;
    private LocalDate enteredDate;
    private Boolean status;
    private Integer sold;

    @NotNull(message ="Hạn sử dụng không được để trống")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date expireDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @Override
    public String toString() {
        return "Product [productId=" + productId + ", name=" + name + ", quantity=" + quantity + ", price=" + price
                + ", discount=" + discount + ", image=" + image + ", description=" + description + ", enteredDate="
                + enteredDate + ", status=" + status + ", sold=" + sold + ", category=" + category + "]";
    }

    public Product(ProductCreateDTO productCreateDTO, CategoryService categoryService){
        this.name = productCreateDTO.getName();
        this.price = productCreateDTO.getPrice();
        this.quantity = productCreateDTO.getQuantity();
        this.description = productCreateDTO.getDescription();
        this.image = productCreateDTO.getImage();
        this.expireDate = productCreateDTO.getExpireDate();
        this.code = productCreateDTO.getCode();
        Category category = categoryService.findByCategoryName(productCreateDTO.getCategory());
        if (category != null) {
            this.category = category;
        } else {
            // Xử lý trường hợp không tìm thấy Category
        }
    }

    public Product(Long id){
        this.productId= id;
    }

    public void decreaseQuantity(int quantity){
        int newQuantity = this.quantity - quantity;
        if (newQuantity >= 0) {
            this.setQuantity(newQuantity);
        } else {

        }
    }

    public void increaseQuantity(int quantity){
        this.quantity += quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
