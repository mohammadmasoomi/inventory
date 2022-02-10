package com.github.mohammadmasoomi.inventory.core.repository.security;

import com.github.mohammadmasoomi.inventory.core.entity.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    @Query(value = "SELECT RP.permissionCode FROM {h-schema}app_user_role UR " +
            "INNER JOIN {h-schema}app_role_permission RP ON UR.roleId = RP.roleId WHERE UR.userId = :userId", nativeQuery = true)
    List<String> findUserPermissionCodes(@Param("userId") long userId);
}
