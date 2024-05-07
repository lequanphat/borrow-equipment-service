package com.example.membersmanagement.mappers;

import com.example.membersmanagement.dtos.BookingDeviceDto;
import com.example.membersmanagement.dtos.ThongTinSd.MuonThietBiDto;
import com.example.membersmanagement.dtos.ThongTinSd.VaoKhuVucHocTapDto;
import com.example.membersmanagement.entities.ThanhVienEntity;
import com.example.membersmanagement.entities.ThietBiEntity;
import com.example.membersmanagement.entities.ThongTinSdEntity;

import java.time.ZoneId;
import java.util.Date;

public class ThongTinSdMapper {
    public static ThongTinSdEntity toThongTinSdFromMuonThietBi(MuonThietBiDto muonThietBiDto) {
        ThanhVienEntity thanhVienEntity = ThanhVienEntity.builder()
                .maTV(Integer.parseInt(muonThietBiDto.getMaTV()))
                .build();
        ThietBiEntity thietBiEntity = ThietBiEntity.builder()
                .maTB(Integer.parseInt(muonThietBiDto.getMaTB()))
                .build();
        return ThongTinSdEntity.builder()
                .thanhVien(thanhVienEntity)
                .thietBi(thietBiEntity)
                .tgMuon(new Date())
                .build();
    }


    public static ThongTinSdEntity toThongTinSdFromBookingDevice(BookingDeviceDto bookingDeviceDTO) {
        ThanhVienEntity thanhVienEntity = ThanhVienEntity.builder()
                .maTV(bookingDeviceDTO.getMaTV())
                .build();
        ThietBiEntity thietBiEntity = ThietBiEntity.builder()
                .maTB(bookingDeviceDTO.getMaTB())
                .build();
        Date tgDatCho = Date.from(bookingDeviceDTO.getTGDatCho().atZone(ZoneId.systemDefault()).toInstant());
        return ThongTinSdEntity.builder()
                .thanhVien(thanhVienEntity)
                .thietBi(thietBiEntity)
                .tgDatCho(tgDatCho).build();
    }

    public static ThongTinSdEntity toThongTinSdFromVaoKhuVucHocTapDto(VaoKhuVucHocTapDto vaoKhuVucHocTapDto) {
        ThanhVienEntity thanhVienEntity = ThanhVienEntity.builder()
                .maTV(Integer.parseInt(vaoKhuVucHocTapDto.getMaTV()))
                .build();
        return ThongTinSdEntity.builder()
                .thanhVien(thanhVienEntity)
                .tgVao(new Date())
                .build();
    }
}
