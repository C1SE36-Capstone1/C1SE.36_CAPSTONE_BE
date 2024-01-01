package com.example.capstone.dto;

import com.example.capstone.model.Cart.Cart;
import com.example.capstone.model.User.Role;

import lombok.Data;
import org.springframework.validation.Errors;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;
import java.time.ZoneId;
@Data
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String code;

//    @NotBlank(message = "Vui lòng nhập họ và tên")
//    @Pattern(regexp = "^(?:[A-Z][a-zÀ-ỹ]*(?: [A-Z][a-zÀ-ỹ]*)+)$",message = "Họ và tên chưa đúng định dạng")
//    @Length(min = 3,max = 50,message = "Họ và tên phải chứa ít nhất 3 kí tự và tối đa 50 kí tự")
    private String name;

//    @NotBlank(message = "Vui lòng nhập email")
//    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",message = "Email không đúng định dạng, vui lòng nhập lại. Ex: tên_email@gmail.com")
//    @Length(min = 6,max = 30,message = "Tên email chỉ được phép chứa từ 6 đến 30 kí tự")
    private String email;

//    @NotBlank(message = "Vui lòng nhập mật khẩu")
//    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{6,32}$", message = "Mật khẩu không hợp lệ")
//    @Size(min = 6, max = 32, message = "Mật khẩu phải có độ dài từ 6 đến 32 ký tự")
    private String password;

//    @NotBlank(message = "Vui lòng nhập số điện thoại")
//    @Pattern(regexp = "^(086|096|097|098|038|037|036|035|034|033|032|091|094|088|081|082|083|084|085|070|076|077|078|079|089|090|093|092|052|056|058|099|059|087)\\d{7}$",
//            message = "số điện thoại chỉ được phép 10 số và đầu số của nhà mạng Việt Nam")
    private String phone;

//    @NotBlank(message = "Vui lòng nhập địa chỉ")
//    @Pattern(regexp = "^[^!@#$%^&*()_+<>?'\"{}\\`~|/\\\\]+$",message = "Địa chỉ không được chứa các kí tự đặc biệt")
//    @Length(min = 5,max = 100,message = "Địa chỉ phải có ít nhất 5 và tối đa 100 kí tự")
    private String address;

    @NotNull(message = "Vui lòng nhập ngày sinh")
    private Date birthdate;

    @NotNull(message = "Vui lòng chọn giới tính")
    private Boolean gender;

    @NotNull(message = "Vui lòng chọn ảnh đại diện")
    private String image;

    private Boolean status;

    private LocalDate registerDate;

    private String token;
    private Cart cart;

    private Set<Role> roles;

    public void validate(Object target, Errors errors) {
        UserInfo customerInfo = (UserInfo) target;
        if (customerInfo.birthdate != null) {
            LocalDate today = LocalDate.now();
            LocalDate minAgeDate = today.minusYears(12);
            LocalDate maxAgeDate = today.minusYears(90);
            LocalDate birthdateLocal = customerInfo.birthdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (birthdateLocal.isAfter(minAgeDate)) {
                errors.rejectValue("dateOfBirth", "", "Chưa đủ 12 tuổi");
            }
            if (birthdateLocal.isBefore(maxAgeDate)) {
                errors.rejectValue("dateOfBirth", "", "Lớn hơn 90 tuổi");
            }
        }
    }

    public UserInfo(Long userId, String code, String name, String email, String phone, String password, String address,
                    Date birthdate, Boolean gender, String image, Boolean status, String token, Cart cart, Set<Role> roles) {
        this.userId = userId;
        this.code = code;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.address = address;
        this.birthdate = birthdate;
        this.gender = gender;
        this.image = image;
        this.status = status;
        this.token = token;
        this.cart = cart;
        this.roles = roles;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
