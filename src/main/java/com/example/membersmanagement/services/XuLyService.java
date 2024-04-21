package com.example.membersmanagement.services;


import com.example.membersmanagement.entities.XuLyEntity;
import com.example.membersmanagement.repositories.XuLyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XuLyService {
    @Autowired
    private XuLyRepository xuLyRepository;

    public List<XuLyEntity> getAll() {
        return xuLyRepository.findAll();
    }
}
