package com.rentals.api.security.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rentals.api.models.ERole;
import com.rentals.api.models.User;

public class MyUserDetails implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String email;

    private String name;

    @JsonIgnore
    private String password;

    private Date created_at;

    private Date updated_at;

    private List<GrantedAuthority> authorities;

    public MyUserDetails(Long id, String name, String email, String password, Date created_at, Date updated_at,
            List<GrantedAuthority> authorities2) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.password = password;
        this.authorities = authorities2;
    }

    public MyUserDetails(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static MyUserDetails build(User user) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(ERole.ROLE_USER.name()));

        return new MyUserDetails(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getCreated_at(),
                user.getUpdated_at(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        MyUserDetails user = (MyUserDetails) o;
        return Objects.equals(id, user.id);
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}