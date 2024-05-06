package com.example.membersmanagement.services;


import com.example.membersmanagement.dtos.ThanhVien.CreateThanhVienDto;
import com.example.membersmanagement.dtos.ThietBi.UpdateThietBiDto;
import com.example.membersmanagement.dtos.XuLy.CreateXuLyDto;
import com.example.membersmanagement.dtos.XuLy.UpdateXuLyDto;
import com.example.membersmanagement.entities.ThanhVienEntity;
import com.example.membersmanagement.entities.ThietBiEntity;
import com.example.membersmanagement.entities.XuLyEntity;
import com.example.membersmanagement.mappers.ThanhVienMapper;
import com.example.membersmanagement.mappers.ThietBiMapper;
import com.example.membersmanagement.mappers.XuLyMapper;
import com.example.membersmanagement.repositories.XuLyRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XuLyService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private XuLyRepository xuLyRepository;
    @Autowired ThanhVienService thanhVienService;

    public List<XuLyEntity> getAll() {
        return xuLyRepository.findAll();
    }

    public List<XuLyEntity> getXuLyByMaTV(int maTV) {
        String jpql = "SELECT xu FROM XuLyEntity xu WHERE xu.thanhVien.maTV = :maTV";
        return entityManager.createQuery(jpql, XuLyEntity.class)
                .setParameter("maTV", maTV)
                .getResultList();
    }

    public XuLyEntity getXuLyByMaTVAndTrangThai(int id, int trangThaiXL) {
        var tv = thanhVienService.findByMaTV(id);
        return xuLyRepository.findByThanhVienAndTrangThaiXL(tv, trangThaiXL);
    }

    public XuLyEntity save(CreateXuLyDto addxulydto) {
        XuLyEntity xuLy = XuLyMapper.toXuLyFromCreate(addxulydto);
        return xuLyRepository.save(xuLy);
    }

    public XuLyEntity getById(int id) {
        return xuLyRepository.findById(id).orElse(null);
    }

    public void update(UpdateXuLyDto xuLyDto) {
        XuLyEntity xuLy = XuLyMapper.toXuLyFromUpdate(xuLyDto);
        xuLyRepository.save(xuLy);
    }

    public void delete(int id) {
        xuLyRepository.deleteById(id);
    }
}
