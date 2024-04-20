package com.example.membersmanagement.services;

import com.example.membersmanagement.entities.ThongTinSdDTO;
import com.example.membersmanagement.repositories.ThongTinSdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThongTinSdService {
    @Autowired
    private ThongTinSdRepository thongTinSdRepository;

    public List<ThongTinSdDTO> getAll() {
        return thongTinSdRepository.findAll();
    }
}
