package com.example.membersmanagement.dtos.ThongKe;

import lombok.Data;

@Data
public class ThongKeLuotVaoDto {
    private String ten;
    private Long luotVao;

    public ThongKeLuotVaoDto(String ten, Long luotVao) {
        this.ten = ten;
        this.luotVao = luotVao;
    }
}
