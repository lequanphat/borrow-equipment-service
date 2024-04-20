package com.example.membersmanagement.entities;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import lombok.ToString;

@Data
@Entity
@Table(name = "thietbi")
@NoArgsConstructor
public class ThietBiDTO {
    @Id
    @Column(name = "MaTB")
    private int maTB;

    @Column(name = "TenTB")
    private String tenTB;

    @Column(name = "motatb")
    private String moTaTB;

    public ThietBiDTO(int maTB, String tenTB, String moTaTB) {
        this.maTB = maTB;
        this.tenTB = tenTB;
        this.moTaTB = moTaTB;
    }
}