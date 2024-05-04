package com.example.membersmanagement.mappers;

import com.example.membersmanagement.dtos.RegistrationDto;
import com.example.membersmanagement.dtos.ThanhVien.CreateThanhVienDto;
import com.example.membersmanagement.dtos.ThanhVien.UpdateThanhVienDto;
import com.example.membersmanagement.entities.ThanhVienEntity;


public class ThanhVienMapper {
    public static ThanhVienEntity toThanhVienFromRegistration(RegistrationDto registrationDto) {
        return ThanhVienEntity.builder()
                .maTV(Integer.valueOf(registrationDto.getMaTv()))
                .email(registrationDto.getEmail())
                .hoTen(registrationDto.getHoTen())
                .khoa(registrationDto.getKhoa())
                .nganh(registrationDto.getNganh())
                .password(registrationDto.getPassword())
                .build();
    }

    public static ThanhVienEntity toThanhvienFromCreate(CreateThanhVienDto addMemberDto) {
        return ThanhVienEntity.builder()
                .maTV(Integer.valueOf(addMemberDto.getMaTV()))
                .hoTen(addMemberDto.getHoTen())
                .khoa(addMemberDto.getKhoa())
                .nganh(addMemberDto.getNganh())
                .sdt(addMemberDto.getSdt())
                .email(addMemberDto.getEmail())
                .password(addMemberDto.getPassword())
                .build();
    }

    public static ThanhVienEntity toThanhVienFromUpdate(UpdateThanhVienDto updateMemberDto) {
        return ThanhVienEntity.builder()
                .maTV(Integer.valueOf(updateMemberDto.getMaTV()))
                .hoTen(updateMemberDto.getHoTen())
                .khoa(updateMemberDto.getKhoa())
                .nganh(updateMemberDto.getNganh())
                .sdt(updateMemberDto.getSdt())
                .email(updateMemberDto.getEmail())
                .password(updateMemberDto.getPassword())
                .build();
    }

    public static CreateThanhVienDto toDto(ThanhVienEntity thanhVienEntity) {
        return CreateThanhVienDto.builder()
                .maTV(String.valueOf(thanhVienEntity.getMaTV()))
                .hoTen(thanhVienEntity.getHoTen())
                .khoa(thanhVienEntity.getKhoa())
                .nganh(thanhVienEntity.getNganh())
                .sdt(thanhVienEntity.getSdt())
                .email(thanhVienEntity.getEmail())
                .password(thanhVienEntity.getPassword())
                .build();
    }

}
