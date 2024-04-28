package com.example.membersmanagement.services;


import com.example.membersmanagement.dtos.ThietBi.CreateThietBiDto;
import com.example.membersmanagement.dtos.ThietBi.UpdateThietBiDto;
import com.example.membersmanagement.entities.ThietBiEntity;
import com.example.membersmanagement.mappers.ThietBiMapper;
import com.example.membersmanagement.repositories.ThietBiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThietBiService {
    @Autowired
    private ThietBiRepository thietBiRepository;

    public List<ThietBiEntity> getAll() {
        return thietBiRepository.findAll();
    }

    public ThietBiEntity save(CreateThietBiDto addDeviceDto) {
        ThietBiEntity newDevice = ThietBiMapper.toThietBiFromCreate(addDeviceDto);
        return thietBiRepository.save(newDevice);
    }

    public boolean existsByMaTb(String maTb) {
        return thietBiRepository.existsById(Integer.valueOf(maTb));
    }

    public ThietBiEntity getById(int id) {
        return thietBiRepository.findById(id).orElse(null);
    }

    public void update(UpdateThietBiDto deviceDto) {
        ThietBiEntity device = ThietBiMapper.toThietBiFromUpdate(deviceDto);
        thietBiRepository.save(device);
    }
}
