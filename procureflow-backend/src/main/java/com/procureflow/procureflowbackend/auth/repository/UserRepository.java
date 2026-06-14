package com.procureflow.procureflowbackend.auth.repository;

import com.procureflow.procureflowbackend.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmailAndIsDeletedFalse(String email);
    @Query(value = """
    SELECT DISTINCT r.role_code
    FROM auth.users u
    JOIN auth.user_roles ur
        ON u.user_id = ur.user_id
    JOIN auth.roles r
        ON ur.role_id = r.role_id
    WHERE u.email = :email
    AND u.is_deleted = 0
    """, nativeQuery = true)
    List<String> findRoleCodesByEmail(@Param("email") String email);


    @Query(value = """
        SELECT DISTINCT p.permission_code
        FROM auth.users u
        JOIN auth.user_roles ur
            ON u.user_id = ur.user_id
        JOIN auth.role_permissions rp
            ON ur.role_id = rp.role_id
        JOIN auth.permissions p
            ON rp.permission_id = p.permission_id
        WHERE u.email = :email
        AND u.is_deleted = 0
        """, nativeQuery = true)
    List<String> findPermissionCodesByEmail(@Param("email") String email);
}
