package com.github.mohammadmasoomi.inventory.core.repository.security;

import com.github.mohammadmasoomi.inventory.core.entity.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "from Role r where r.code = :code")
    Role findRoleByCode(@Param("code") String code);
}
