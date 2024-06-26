
package com.example.membersmanagement.mappers;

import com.example.membersmanagement.dtos.ThietBi.CreateThietBiDto;
import com.example.membersmanagement.dtos.ThietBi.ReadThietBiDto;
import com.example.membersmanagement.dtos.ThietBi.UpdateThietBiDto;
import com.example.membersmanagement.entities.ThietBiEntity;
import com.example.membersmanagement.enums.TinhTrangThietBi;

public class ThietBiMapper {
    public static ThietBiEntity toThietBiFromCreate(CreateThietBiDto addDeviceDto) {
        return ThietBiEntity.builder()
                .maTB(Integer.valueOf(addDeviceDto.getMaTB()))
                .tenTB(addDeviceDto.getTenTB())
                .moTaTB(addDeviceDto.getMoTaTB())
                .build();
    }

    public static ThietBiEntity toThietBiFromUpdate(UpdateThietBiDto updateDeviceDto) {
        return ThietBiEntity.builder()
                .maTB(Integer.valueOf(updateDeviceDto.getMaTB()))
                .tenTB(updateDeviceDto.getTenTB())
                .moTaTB(updateDeviceDto.getMoTaTB())
                .build();
    }

    public static CreateThietBiDto toDto(ThietBiEntity thietBiEntity) {
        return CreateThietBiDto.builder()
                .maTB(String.valueOf(thietBiEntity.getMaTB()))
                .tenTB(thietBiEntity.getTenTB())
                .moTaTB(thietBiEntity.getMoTaTB())
                .build();
    }

    public static ReadThietBiDto toReadDto(ThietBiEntity thietBiEntity, TinhTrangThietBi tinhTrangThietBi) {
        return ReadThietBiDto.builder()
                .maTB(String.valueOf(thietBiEntity.getMaTB()))
                .tenTB(thietBiEntity.getTenTB())
                .moTaTB(thietBiEntity.getMoTaTB())
                .tinhTrang(tinhTrangThietBi)
                .build();
    }
}
