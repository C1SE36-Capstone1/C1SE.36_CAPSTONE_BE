package com.example.capstone.repository.User;

import com.example.capstone.model.Cart.Cart;
import com.example.capstone.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.servlet.annotation.MultipartConfig;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    List<User> findByStatusTrue();

    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    User findByToken(String token);

    @Query(value = "select * from  users order by code desc limit 1",nativeQuery = true)
    User limitUser();

    @Modifying
    @Query(value = "UPDATE users SET address = :address, email = :email, gender = :gender, image = :image, name = :name, cart = :cart_id, phone = :phone, birthdate = :birthdate WHERE user_id = :user_id", nativeQuery = true)
    void updateUser(@Param("user_id") Integer user_id,
                    @Param("address") String address,
                    @Param("email") String email,
                    @Param("gender") Boolean gender,
                    @Param("image") String image,
                    @Param("name") String name,
                    @Param("cart_id") Cart cart,
                    @Param("phone") String phone,
                    @Param("birthdate") Date birthdate);

    @Modifying
    @Query(value = "update users set status = false where user_id = :id", nativeQuery = true)
    void deleteUserId(@Param("id") Integer id);

    @Modifying
    @Query(value = "INSERT INTO users (address, code, email, birthdate, gender, image, name, register_date, password, phone, status,token, cart_id) VALUES (:address, :code, :email,:birthdate, :gender, :image, :name, :register_date, :password, :phone,:status, :token, :cart_id)",nativeQuery = true)
    void insertUser(@Param("name") String name,
                    @Param("email") String email,
                    @Param("code") String code,
                    @Param("phone") String phone,
                    @Param("address") String address,
                    @Param("birthdate") Date birthdate,
                    @Param("password") String password,
                    @Param("register_date") LocalDate register_date,
                    @Param("gender") Boolean gender,
                    @Param("image") String image,
                    @Param("status") Boolean status,
                    @Param("token") String token,
                    @Param("cart_id") Integer cart_id
                    );


}

