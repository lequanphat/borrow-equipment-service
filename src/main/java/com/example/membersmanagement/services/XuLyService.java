package com.example.membersmanagement.services;


import com.example.membersmanagement.dtos.XuLy.CreateXuLyDto;
import com.example.membersmanagement.dtos.XuLy.UpdateXuLyDto;
import com.example.membersmanagement.entities.XuLyEntity;
import com.example.membersmanagement.mappers.XuLyMapper;
import com.example.membersmanagement.repositories.XuLyRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class XuLyService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private XuLyRepository xuLyRepository;
    @Autowired
    ThanhVienService thanhVienService;

    public List<XuLyEntity> getAll() {
        return xuLyRepository.findAll();
    }

    public List<XuLyEntity> getXuLyByMaTV(int maTV) {
        return xuLyRepository.findByThanhVienMaTVOrderByNgayXLDesc(maTV);
    }

    public int getTongTien() {
        List<XuLyEntity> list = xuLyRepository.findAll();
        return xuLyRepository.sumTongTien(list);
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

    public Page<XuLyEntity> filterViolations(String search, int trangthai, LocalDate tgBatDau, LocalDate tgKetThuc, Pageable paging) {
        String jpql = "SELECT xl " +
                "FROM XuLyEntity xl " +
                "WHERE xl.ngayXL BETWEEN :tgBatDau AND :tgKetThuc AND (xl.thanhVien.hoTen LIKE :search OR CAST(xl.thanhVien.maTV AS string) LIKE :search) " +
                "AND (:trangthai = 3 OR xl.trangThaiXL = :trangthai) ORDER BY xl.ngayXL DESC";

        // Create the query
        Query query = entityManager.createQuery(jpql, XuLyEntity.class)
                .setParameter("tgBatDau", java.sql.Date.valueOf(tgBatDau))
                .setParameter("tgKetThuc", java.sql.Date.valueOf(tgKetThuc))
                .setParameter("search", "%" + search + "%")
                .setParameter("trangthai", trangthai);
        int totalResults = query.getResultList().size();
        // Execute the query and get the result list
        List<XuLyEntity> resultList = query
                .setFirstResult(paging.getPageNumber() * paging.getPageSize())
                .setMaxResults(paging.getPageSize())
                .getResultList();

        // Create and return a Page object
        return new PageImpl<>(resultList, paging, totalResults);
    }

    public boolean canBorrowDevice(int maTV) {
        return !xuLyRepository.existsByThanhVienMaTVAndTrangThaiXL(maTV, 1);
    }
}