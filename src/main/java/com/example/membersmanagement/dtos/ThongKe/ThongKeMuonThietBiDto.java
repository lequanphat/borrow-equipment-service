package com.example.membersmanagement.dtos.ThongKe;

import lombok.Data;

@Data
public class ThongKeMuonThietBiDto {
    private Integer maTB;
    private String tenTB;
    private String moTaTB;
    private Long count;

    public ThongKeMuonThietBiDto(Integer maTB, String tenTB, String moTaTB, Long count) {
        this.maTB = maTB;
        this.tenTB = tenTB;
        this.moTaTB = moTaTB;
        this.count = count;
    }
}
