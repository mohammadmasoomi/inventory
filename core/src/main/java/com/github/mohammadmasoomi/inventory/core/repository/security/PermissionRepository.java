package com.github.mohammadmasoomi.inventory.core.repository.security;

import com.github.mohammadmasoomi.inventory.core.entity.security.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query(value = "from Permission p where p.code = :code")
    Permission findPermissionByCode(@Param("code") String code);

}
