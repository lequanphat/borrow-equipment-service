package com.example.membersmanagement.entities;

import jakarta.persistence.*;

import java.util.Date;

import lombok.Data;

@Data
@Entity
@Table(name = "thongtinsd")
public class ThongTinSdEntity {
    @Id
    @Column(name = "MaTT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maTT;

    @ManyToOne
    @JoinColumn(name = "MaTV")
    private ThanhVienEntity thanhVien;

    @ManyToOne
    @JoinColumn(name = "MaTB")
    private ThietBiEntity thietBi;

    @Column(name = "TGVao")
    private Date tgVao;

    @Column(name = "TGMuon")
    private Date tgMuon;

    @Column(name = "TGTra")
    private Date tgTra;

    @Column(name = "TGDatcho")
    private Date tgDatCho;
}