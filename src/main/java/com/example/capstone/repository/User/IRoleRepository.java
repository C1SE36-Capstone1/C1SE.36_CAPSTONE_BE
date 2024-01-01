package com.example.capstone.repository.User;

import com.example.capstone.model.User.Role;
import com.example.capstone.model.User.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);

    @Modifying
    @Query(value = "INSERT INTO user_roles (user_id, role_id) VALUES (:user_id, :role_id)", nativeQuery = true)
    void insertUserRole(@Param("user_id") Long userId, @Param("role_id") Long roleId);
}
