package com.example.membersmanagement.services;


import com.example.membersmanagement.dtos.ThanhVien.CreateThanhVienDto;
import com.example.membersmanagement.dtos.ThietBi.UpdateThietBiDto;
import com.example.membersmanagement.dtos.ThongKe.ThongKeMuonThietBiDto;
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

    public Page<XuLyEntity> TimKiem(String search, int trangthai, LocalDate tgBatDau, LocalDate tgKetThuc, Pageable paging) {
        String jpql = "SELECT xl " +
                "FROM XuLyEntity xl " +
                "WHERE xl.ngayXL BETWEEN :tgBatDau AND :tgKetThuc AND (xl.thanhVien.hoTen LIKE :search OR CAST(xl.thanhVien.maTV AS string) LIKE :search) ";

        if (trangthai != 3) {
            jpql = "SELECT xl " +
                    "FROM XuLyEntity xl " +
                    "WHERE xl.ngayXL BETWEEN :tgBatDau AND :tgKetThuc AND (xl.thanhVien.hoTen LIKE :search OR CAST(xl.thanhVien.maTV AS string) LIKE :search) " +
                    "AND xl.trangThaiXL = :trangthai";
            return createQueryAndPaginate(jpql, search, trangthai, tgBatDau, tgKetThuc, paging);
        } else {
            return createQueryAndPaginate(jpql, search, tgBatDau, tgKetThuc, paging);
        }
    }

    private Page<XuLyEntity> createQueryAndPaginate(String jpql, String search, int trangthai, LocalDate tgBatDau, LocalDate tgKetThuc, Pageable paging) {
        Query query = entityManager.createQuery(jpql, XuLyEntity.class)
                .setParameter("tgBatDau", java.sql.Date.valueOf(tgBatDau))
                .setParameter("tgKetThuc", java.sql.Date.valueOf(tgKetThuc))
                .setParameter("search", "%" + search + "%")
                .setParameter("trangthai", trangthai);

        return getPageResult(query, paging);
    }

    private Page<XuLyEntity> createQueryAndPaginate(String jpql, String search, LocalDate tgBatDau, LocalDate tgKetThuc, Pageable paging) {
        Query query = entityManager.createQuery(jpql, XuLyEntity.class)
                .setParameter("tgBatDau", java.sql.Date.valueOf(tgBatDau))
                .setParameter("tgKetThuc", java.sql.Date.valueOf(tgKetThuc))
                .setParameter("search", "%" + search + "%");

        return getPageResult(query, paging);
    }

    private Page<XuLyEntity> getPageResult(Query query, Pageable paging) {
        query.setFirstResult(paging.getPageNumber() * paging.getPageSize());
        query.setMaxResults(paging.getPageSize());

        List<XuLyEntity> result = query.getResultList();

        // Đếm tổng số lượng kết quả
        int totalResults = result.size();

        return new PageImpl<>(result, paging, totalResults);
    }

}
