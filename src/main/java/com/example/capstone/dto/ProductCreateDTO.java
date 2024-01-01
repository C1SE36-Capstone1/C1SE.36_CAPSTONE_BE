package com.example.capstone.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
//@AllArgsConstructor
public class ProductCreateDTO {
    private Long productId;

//    @NotBlank(message = "Tên sản phẩm không được để trống")
//    @Pattern(regexp = "^[^!@#$%^&*()_+<>?'\"{}\\`~|/\\\\]+$", message = "Tên vật tư không được chứa các kí tự đặc biệt")
    private String name;

//    @NotNull(message = "Giá sản phẩm không được để trống")
//    @Min(value = 1,message = "Giá vật không được bé hơn 0")
//    @Max(value = 1000000000, message = "giá vượt giá 10000000")
    private Double price;

//    @NotNull(message = "Số lượng sản phẩm không được để trống")
//    @Min(value = 1,message = "Vật tư không được bé hơn 0")
//    @Max(value = 10000, message = "số lượng không vượt quá 10000")
    private Integer quantity;

//    @NotBlank(message = "Ảnh sản phẩm không được để trống")
//    @Size(min = 0,max = 1000,message = "Hình ảnh không phù hợp")
    private String image;

    private String code;

//    @NotNull(message ="Hạn sử dụng không được để trống")
//    @JsonFormat(pattern="yyyy-MM-dd")
    private Date expireDate;

//    @NotBlank(message = "loại sản phẩm không được để trống")
    private String category;

    private String description;

    private boolean status;
    public ProductCreateDTO(Long productId, String name, Double price, Integer quantity, String image, String code, Date expireDate, String description, String category) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.code = code;
        this.expireDate = expireDate;
        this.description = description;
        this.category = category;
    }
}
