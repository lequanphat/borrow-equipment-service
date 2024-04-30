package com.example.membersmanagement.entities;

import jakarta.persistence.*;

import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@Table(name = "thongtinsd")
@NoArgsConstructor
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

    public ThongTinSdEntity(int maTT, ThanhVienEntity thanhVien, ThietBiEntity thietBi, Date tgVao, Date tgMuon, Date tgTra, Date tgDatCho) {
        this.maTT = maTT;
        this.thanhVien = thanhVien;
        this.thietBi = thietBi;
        this.tgVao = tgVao;
        this.tgMuon = tgMuon;
        this.tgTra = tgTra;
        this.tgDatCho = tgDatCho;
    }
}