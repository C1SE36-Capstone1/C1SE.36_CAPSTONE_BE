package com.example.capstone.repository.User;

import com.example.capstone.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
}

