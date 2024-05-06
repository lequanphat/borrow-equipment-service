package com.example.membersmanagement.mappers;


import com.example.membersmanagement.dtos.ThanhVien.CreateThanhVienDto;
import com.example.membersmanagement.dtos.ThietBi.CreateThietBiDto;
import com.example.membersmanagement.dtos.XuLy.CreateXuLyDto;
import com.example.membersmanagement.dtos.XuLy.UpdateXuLyDto;
import com.example.membersmanagement.entities.ThietBiEntity;
import com.example.membersmanagement.entities.XuLyEntity;
import com.example.membersmanagement.entities.ThanhVienEntity;
public class XuLyMapper {
    public static XuLyEntity toXuLyFromCreate(CreateXuLyDto addViolationDto) {
        ThanhVienEntity thanhVienEntity =  ThanhVienEntity.builder()
                .maTV(Integer.valueOf(addViolationDto.getMaTV()))
                .build();
        return XuLyEntity.builder()
                .thanhVien(thanhVienEntity)
                .hinhThucXL(addViolationDto.getHinhThucXL())
                .soTien(addViolationDto.getSoTien())
                .ngayXL(addViolationDto.getNgayXL())
                .trangThaiXL(Integer.valueOf(addViolationDto.getTrangThaiXL()))
                .build();
    }

    public static UpdateXuLyDto toDto(XuLyEntity xuLyEntity){
        return UpdateXuLyDto.builder()
                .maXL(String.valueOf(xuLyEntity.getMaXL()))
                .maTV(String.valueOf(xuLyEntity.getThanhVien().getMaTV()))
                .hinhThucXL(xuLyEntity.getHinhThucXL())
                .soTien(xuLyEntity.getSoTien())
                .ngayXL(xuLyEntity.getNgayXL())
                .trangThaiXL(xuLyEntity.getTrangThaiXL())
                .build();
    }

    public static XuLyEntity toXuLyFromUpdate(UpdateXuLyDto addViolationDto) {
        ThanhVienEntity thanhVienEntity =  ThanhVienEntity.builder()
                .maTV(Integer.valueOf(addViolationDto.getMaTV()))
                .build();
        return XuLyEntity.builder()
                .maXL(Integer.valueOf(addViolationDto.getMaXL()))
                .thanhVien(thanhVienEntity)
                .hinhThucXL(addViolationDto.getHinhThucXL())
                .soTien(addViolationDto.getSoTien())
                .ngayXL(addViolationDto.getNgayXL())
                .trangThaiXL(Integer.valueOf(addViolationDto.getTrangThaiXL()))
                .build();
    }
}
