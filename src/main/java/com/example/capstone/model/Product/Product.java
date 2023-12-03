package com.example.capstone.model.Product;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;
    private String code;
    private String name;
    private int quantity;
    private Double price;
    private int discount;
    private String image;
    private String description;
    private LocalDate enteredDate;
    private Boolean status;
    private int sold;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @Override
    public String toString() {
        return "Product [productId=" + productId + ", name=" + name + ", quantity=" + quantity + ", price=" + price
                + ", discount=" + discount + ", image=" + image + ", description=" + description + ", enteredDate="
                + enteredDate + ", status=" + status + ", sold=" + sold + ", category=" + category + "]";
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
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
