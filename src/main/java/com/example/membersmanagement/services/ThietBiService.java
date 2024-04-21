package com.example.membersmanagement.services;


import com.example.membersmanagement.entities.ThietBiEntity;
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
}
