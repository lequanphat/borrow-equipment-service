package com.example.membersmanagement.services;

import com.example.membersmanagement.entities.ThongTinSdEntity;
import com.example.membersmanagement.entities.XuLyEntity;
import com.example.membersmanagement.repositories.ThongTinSdRepository;
import com.example.membersmanagement.repositories.XuLyRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThongTinSdService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private ThongTinSdRepository thongTinSdRepository;

    public List<ThongTinSdEntity> getAll() {
        return thongTinSdRepository.findAll();
    }

    public List<ThongTinSdEntity> getThietBiDangDatChoByMaTV(int maTV) {
        String jpql = "SELECT ttsd FROM ThongTinSdEntity ttsd WHERE ttsd.thanhVien.maTV = :maTV and ttsd.tgDatCho is not null ";
        return entityManager.createQuery(jpql, ThongTinSdEntity.class)
                .setParameter("maTV", maTV)
                .getResultList();
    }

    public List<ThongTinSdEntity> getThietBiDangMuonByMaTV(int maTV) {
        String jpql = "SELECT ttsd FROM ThongTinSdEntity ttsd WHERE ttsd.thanhVien.maTV = :maTV and ttsd.tgMuon is not null ";
        return entityManager.createQuery(jpql, ThongTinSdEntity.class)
                .setParameter("maTV", maTV)
                .getResultList();
    }
    
    public List<ThongTinSdEntity> getDsDatCho() {
        return thongTinSdRepository.findByTgMuonIsNotNull();
    }
}

