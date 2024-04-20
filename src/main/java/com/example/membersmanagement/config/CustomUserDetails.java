package com.example.membersmanagement.config;

import com.example.membersmanagement.entities.ThanhVienDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private String email;
    private String password;
    private String hoTen;
    private int maTV;

    public CustomUserDetails(ThanhVienDTO thanhVienDTO) {
        this.email = thanhVienDTO.getEmail();
        this.password = thanhVienDTO.getPassword();
        this.hoTen = thanhVienDTO.getHoTen();
        this.maTV = thanhVienDTO.getMaTV();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getHoTen() {
        return hoTen;
    }

    public int getMaTV() {
        return maTV;
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
}
