package com.example.capstone.repository.User;

import com.example.capstone.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

    @Query(value = "select * from  users order by 'code' desc limit 1",nativeQuery = true)
    User limitUser();

//    @Query(value = "UPDATE users SET `address` = :address,`code` = :code,`email` = :email,`gender` = :gender,`image` = :image,`name` = :name,`password` = :password,`phone` = :phone,`register_date` = :register_date,`status` = :status,`token` = :token,`birthdate` = :birthdate,WHERE (`user_id` = :user_id)", nativeQuery = true)
//    void updateUser(@Param("customer_id") Integer id,
//                    @Param("address") String address,
//                    @Param("code") String code,
//                    @Param("email") String email,
//                    @Param("gender") Boolean gender,
//                    @Param("image") String image,
//                    @Param("name") String name,
//                    @Param("password") String password,
//                    @Param("phone") String phone,
//                    @Param("register_date") LocalDate register_date,
//                    @Param("status") Boolean status,
//                    @Param("token") String token,
//                    @Param("birthdate") Date birthdate);
}

