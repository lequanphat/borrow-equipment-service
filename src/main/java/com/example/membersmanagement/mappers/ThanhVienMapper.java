package com.example.membersmanagement.mappers;

import com.example.membersmanagement.dtos.RegistrationDto;
import com.example.membersmanagement.entities.ThanhVienEntity;

public class ThanhVienMapper {
    public static ThanhVienEntity toThanhVienFromRegistration(RegistrationDto registrationDto) {
        return ThanhVienEntity.builder()
                .maTV(Integer.valueOf(registrationDto.getMaTv()))
                .email(registrationDto.getEmail())
                .hoTen(registrationDto.getHoTen())
                .password(registrationDto.getPassword())
                .build();
    }
}
