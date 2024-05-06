package com.example.membersmanagement.mappers;


import com.example.membersmanagement.dtos.XuLy.CreateXuLyDto;
import com.example.membersmanagement.entities.XuLyEntity;
import com.example.membersmanagement.entities.ThanhVienEntity;
public class XuLyMapper {
    public static XuLyEntity toXuLyFromCreate(CreateXuLyDto addViolationDto) {
        ThanhVienEntity thanhVienEntity =  ThanhVienEntity.builder()
                .maTV(addViolationDto.getMaTV())
                .build();
        return XuLyEntity.builder()
                .thanhVien(thanhVienEntity)
                .hinhThucXL(addViolationDto.getHinhThucXL())
                .soTien(addViolationDto.getSoTien())
                .ngayXL(addViolationDto.getNgayXL())
                .trangThaiXL(Integer.valueOf(addViolationDto.getTrangThaiXL()))
                .build();
    }
}
