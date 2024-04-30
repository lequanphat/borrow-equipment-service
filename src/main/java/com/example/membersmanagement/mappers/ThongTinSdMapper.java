package com.example.membersmanagement.mappers;

import com.example.membersmanagement.dtos.ThongTinSd.MuonThietBiDto;
import com.example.membersmanagement.dtos.ThongTinSd.TraThietBiDto;
import com.example.membersmanagement.entities.ThanhVienEntity;
import com.example.membersmanagement.entities.ThietBiEntity;
import com.example.membersmanagement.entities.ThongTinSdEntity;

import java.util.Date;

public class ThongTinSdMapper {
    public static ThongTinSdEntity toThongTinSdFromMuonThietBi(MuonThietBiDto muonThietBiDto) {
        ThanhVienEntity thanhVienEntity =  ThanhVienEntity.builder()
                .maTV(muonThietBiDto.getMaTV())
                .build();
        ThietBiEntity thietBiEntity = ThietBiEntity.builder()
                .maTB(muonThietBiDto.getMaTB())
                .build();
        return ThongTinSdEntity.builder()
                .thanhVien(thanhVienEntity)
                .thietBi(thietBiEntity)
                .tgMuon(new Date())
                .build();
    }
}
