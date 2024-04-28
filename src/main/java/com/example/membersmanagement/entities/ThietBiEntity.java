package com.example.membersmanagement.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@Table(name = "thietbi")
@NoArgsConstructor
public class ThietBiEntity {
    @Id
    @Column(name = "MaTB")
    private int maTB;

    @Column(name = "TenTB")
    private String tenTB;

    @Column(name = "motatb")
    private String moTaTB;

    public ThietBiEntity(int maTB, String tenTB, String moTaTB) {
        this.maTB = maTB;
        this.tenTB = tenTB;
        this.moTaTB = moTaTB;
    }
}