package com.example.membersmanagement.services;


import com.example.membersmanagement.entities.XuLyDTO;
import com.example.membersmanagement.repositories.XuLyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XuLyService {
    @Autowired
    private XuLyRepository xuLyRepository;

    public List<XuLyDTO> getAll() {
        return xuLyRepository.findAll();
    }
}
