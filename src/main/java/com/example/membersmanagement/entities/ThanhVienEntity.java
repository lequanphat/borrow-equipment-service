package com.example.membersmanagement.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@Table(name = "thanhvien")
@NoArgsConstructor
public class ThanhVienEntity {
    @Id
    @Column(name = "MaTV")
    private int maTV;

    @Column(name = "hoten")
    private String hoTen;

    @Column(name = "Khoa")
    private String khoa;

    @Column(name = "Nganh")
    private String nganh;

    @Column(name = "SDT")
    private String sdt;

    @Column(name = "Email")
    private String email;

    @Column(name = "Password")
    private String password;

    public ThanhVienEntity(int maTV, String hoTen, String khoa, String nganh, String sdt, String email, String password) {
        this.maTV = maTV;
        this.hoTen = hoTen;
        this.khoa = khoa;
        this.nganh = nganh;
        this.sdt = sdt;
        this.email = email;
        this.password = password;
    }
}