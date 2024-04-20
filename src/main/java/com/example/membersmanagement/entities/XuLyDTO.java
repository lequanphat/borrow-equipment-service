package com.example.membersmanagement.entities;
import java.util.Date;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name = "xuly")
@NoArgsConstructor
public class XuLyDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaXL")
    private int maXL;

    @ManyToOne
    @JoinColumn(name = "MaTV")
    @ToString.Exclude
    private ThanhVienDTO thanhVien;

    @Column(name = "hinhthucxl")
    private String hinhThucXL;

    @Column(name = "sotien")
    private Integer soTien;

    @Column(name = "NgayXL")
    private Date ngayXL;

    @Column(name = "trangthaixl")
    private Integer trangThaiXL;

    public XuLyDTO(int maXL, ThanhVienDTO thanhVien, String hinhThucXL, Integer soTien, Date ngayXL, Integer trangThaiXL) {
        this.maXL = maXL;
        this.thanhVien = thanhVien;
        this.hinhThucXL = hinhThucXL;
        this.soTien = soTien;
        this.ngayXL = ngayXL;
        this.trangThaiXL = trangThaiXL;
    }

    public XuLyDTO( ThanhVienDTO thanhVien, String hinhThucXL, Integer soTien, Date ngayXL, Integer trangThaiXL) {
        this.thanhVien = thanhVien;
        this.hinhThucXL = hinhThucXL;
        this.soTien = soTien;
        this.ngayXL = ngayXL;
        this.trangThaiXL = trangThaiXL;
    }
}