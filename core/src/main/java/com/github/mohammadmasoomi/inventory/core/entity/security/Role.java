package com.github.mohammadmasoomi.inventory.core.entity.security;

import com.github.mohammadmasoomi.inventory.core.entity.base.BaseEntity;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "app_role")
public class Role extends BaseEntity {

    @Column(length = 3, nullable = false, unique = true)
    private String code;

    @Column(length = 30, nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "app_role_permission", joinColumns = {
            @JoinColumn(name = "roleId", referencedColumnName = "id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "permissionCode", referencedColumnName = "code", nullable = false, updatable = false)})
    private Set<Permission> permissions;

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

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(code, role.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
