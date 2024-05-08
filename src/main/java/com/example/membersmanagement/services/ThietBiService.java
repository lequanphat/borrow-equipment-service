package com.example.membersmanagement.services;


import com.example.membersmanagement.dtos.ThietBi.CreateThietBiDto;
import com.example.membersmanagement.dtos.ThietBi.ReadThietBiDto;
import com.example.membersmanagement.dtos.ThietBi.UpdateThietBiDto;
import com.example.membersmanagement.entities.ThietBiEntity;
import com.example.membersmanagement.enums.TinhTrangThietBi;
import com.example.membersmanagement.mappers.ThietBiMapper;
import com.example.membersmanagement.repositories.ThietBiRepository;
import com.example.membersmanagement.repositories.ThongTinSdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ThietBiService {
    @Autowired
    private ThietBiRepository thietBiRepository;
    @Autowired
    private ThongTinSdRepository thongTinSdRepository;

    public Page<ThietBiEntity> getAllDevices(String keyword, Pageable paging) {
        return thietBiRepository.findBySearchText(keyword.trim(), paging);
    }

    public ThietBiEntity save(CreateThietBiDto addDeviceDto) {
        ThietBiEntity newDevice = ThietBiMapper.toThietBiFromCreate(addDeviceDto);
        return thietBiRepository.save(newDevice);
    }

    public void saveAll(List<ThietBiEntity> devices) {
        thietBiRepository.saveAll(devices);
    }

    public boolean existsByMaTb(int maTb) {
        return thietBiRepository.existsById(maTb);
    }

    public ThietBiEntity getById(int id) {
        return thietBiRepository.findById(id).orElse(null);
    }

    public boolean isBorrowedOrBooked(int id) {
        return isBorrowed(id) || isBooked(id);
    }

    public boolean isBorrowedOrBookedAtThatTime(int id, LocalDate date) {
        return isBorrowedAtThatTime(id, date) || isBookedAtThatTime(id, date);
    }

    public boolean isBorrowedOrBookedAtThatTimeByAnotherMember(int maTB, int maTV, LocalDate date) {
        return isBorrowedAtThatTime(maTB, date) || isBookedAtThatTimeByAnotherMember(maTB, maTV, date);
    }

    public boolean isBorrowedAtThatTime(int id, LocalDate date) {
        return thongTinSdRepository.existsByThietBiMaTBAndTgMuonIsNotNullAndTgTraIsNullAndTgMuon(id, date);
    }

    public boolean isBookedAtThatTime(int id, LocalDate date) {
        return thongTinSdRepository.existsByThietBiMaTBAndTgDatChoIsNotNullAndTgDatCho(id, date);
    }

    public boolean isBookedAtThatTimeByAnotherMember(int maTB, int maTV, LocalDate date) {
        return thongTinSdRepository.isBorrowingByAnotherMember(maTB, maTV, date);
    }

    public void update(UpdateThietBiDto deviceDto) {
        ThietBiEntity device = ThietBiMapper.toThietBiFromUpdate(deviceDto);
        thietBiRepository.save(device);
    }

    public void delete(int id) {
        thietBiRepository.deleteById(id);
    }

    public boolean isBorrowed(int id) {
        return thongTinSdRepository.existsByThietBiMaTBAndTgMuonIsNotNullAndTgTraIsNull(id);
    }

    public boolean isBooked(int id) {
        return thongTinSdRepository.existsByThietBiMaTBAndTgDatChoIsNotNull(id);
    }

    public boolean isBookedByMember(int maTB, int maTV) {
        return thongTinSdRepository.existsByThietBiMaTBAndTgDatChoIsNotNullAndThanhVienMaTV(maTB, maTV);
    }

    @Transactional
    public void multipleDelete(int maLoai) {
        thietBiRepository.multipleDeleteByMaLoai(String.valueOf(maLoai));
    }

}
