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

import java.util.List;

@Service
public class ThietBiService {
    @Autowired
    private ThietBiRepository thietBiRepository;
    @Autowired
    private ThongTinSdRepository thongTinSdRepository;

    public Page<ReadThietBiDto> getAll(String keyword, Pageable paging) {
        Page<ThietBiEntity> entities = thietBiRepository.findByTenTBContainingIgnoreCase(keyword, paging);
        return entities.map(thietBiEntity ->
                ThietBiMapper.toReadDto(
                        thietBiEntity,
                        getTinhTrang(thietBiEntity.getMaTB())
                )
        );
    }

    public ThietBiEntity save(CreateThietBiDto addDeviceDto) {
        ThietBiEntity newDevice = ThietBiMapper.toThietBiFromCreate(addDeviceDto);
        return thietBiRepository.save(newDevice);
    }

    public void saveAll(List<ThietBiEntity> devices) {
        thietBiRepository.saveAll(devices);
    }

    public List<ThietBiEntity> getAllDevices() {
        return thietBiRepository.findAll();
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

    public TinhTrangThietBi getTinhTrang(int id) {
        if (isBorrowed(id)) {
            return TinhTrangThietBi.BORROWED;
        } else if (isBooked(id)) {
            return TinhTrangThietBi.BOOKED;
        } else {
            return TinhTrangThietBi.AVAILABLE;
        }
    }

    @Transactional
    public void multipleDelete(int maLoai) {
        thietBiRepository.multipleDeleteByMaLoai(String.valueOf(maLoai));
    }
}
