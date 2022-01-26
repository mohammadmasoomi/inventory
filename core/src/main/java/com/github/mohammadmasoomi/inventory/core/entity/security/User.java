package com.github.mohammadmasoomi.inventory.core.entity.security;

import com.github.mohammadmasoomi.inventory.core.entity.base.BaseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "app_user")
public class User extends BaseEntity implements UserDetails {

    @Column(nullable = false, unique = true, length = 20)
    private String username;

    @Column(nullable = false, length = 400)
    private String password;

    @Column(nullable = false)
    private boolean enabled;

    @ManyToMany
    @JoinTable(name = "app_user_role", joinColumns = {
            @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "roleId", referencedColumnName = "id",
                    nullable = false, updatable = false)})
    private Set<Role> roles;

    @Transient
    private boolean credentialsNonExpired;

    @Transient
    private boolean accountNonLocked;

    @Transient
    private List<GrantedAuthority> authorities;

    public User() {
    }

    public User(String username, String password, boolean enabled, boolean credentialsNonExpired,
                boolean accountNonLocked, List<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    public void addRole(Role role) {
        if (roles == null) {
            roles = new HashSet<>();
        }
        roles.add(role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}