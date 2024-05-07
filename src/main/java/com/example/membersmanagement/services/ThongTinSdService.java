package com.example.membersmanagement.services;

import com.example.membersmanagement.dtos.ThongKe.ThongKeLuotVaoDto;
import com.example.membersmanagement.dtos.ThongKe.ThongKeMuonThietBiDto;
import com.example.membersmanagement.entities.ThongTinSdEntity;
import com.example.membersmanagement.repositories.ThongTinSdRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
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
        return thongTinSdRepository.findByThanhVienMaTVAndTgDatChoIsNotNullAndTgMuonIsNull(maTV);
    }

    public List<ThongTinSdEntity> getThietBiDangMuonByMaTV(int maTV) {
        return thongTinSdRepository.findByThanhVienMaTVAndTgMuonIsNotNullAndTgTraIsNull(maTV);
    }

    public ThongTinSdEntity save(ThongTinSdEntity thongTinSdEntity) {
        return thongTinSdRepository.save(thongTinSdEntity);
    }

    public ThongTinSdEntity traThietBi(int maTB) {
        ThongTinSdEntity thongTinSdEntity = thongTinSdRepository.
                findByThietBiMaTBAndTgMuonIsNotNullAndTgTraIsNull(maTB);
        thongTinSdEntity.setTgTra(new Date());
        return thongTinSdRepository.save(thongTinSdEntity);
    }

    public List<ThongKeLuotVaoDto> thongKeLuotVao(String thongKeTheo, LocalDate tgBatDau, LocalDate tgKetThuc) {
        String groupByField = thongKeTheo.equals("khoa") ? "ttsd.thanhVien.khoa" : "ttsd.thanhVien.nganh";

        String jpql = "SELECT new com.example.membersmanagement.dtos.ThongKe.ThongKeLuotVaoDto(" + groupByField + ", count(ttsd.thanhVien.maTV)) " +
                "FROM ThongTinSdEntity ttsd " +
                "WHERE ttsd.tgVao BETWEEN :tgBatDau AND :tgKetThuc " +
                "GROUP BY " + groupByField;

        return entityManager.createQuery(jpql, ThongKeLuotVaoDto.class)
                .setParameter("tgBatDau", java.sql.Date.valueOf(tgBatDau))
                .setParameter("tgKetThuc", java.sql.Date.valueOf(tgKetThuc))
                .getResultList();
    }

    public List<ThongTinSdEntity> getDsThietBiDangDuocMuon(String tenTB) {
        return thongTinSdRepository.findByThietBiTenTBContainingIgnoreCaseAndTgMuonIsNotNullAndTgTraIsNull(tenTB);
    }

    public Page<ThongTinSdEntity> getDsDatChoThietBi(String keyword, Pageable paging) {
        return thongTinSdRepository.findByThietBiTenTBContainingIgnoreCaseAndTgDatChoIsNotNullAndTgMuonIsNull(keyword, paging);
    }

    public List<ThongKeMuonThietBiDto> thongKeMuonThietBiTheoNgay(LocalDate tgBatDau, LocalDate tgKetThuc) {
        String jpql = "SELECT ttsd.thietBi.maTB, ttsd.thietBi.tenTB, ttsd.thietBi.moTaTB, count(ttsd.thietBi.maTB) " +
                "FROM ThongTinSdEntity ttsd " +
                "WHERE ttsd.tgMuon BETWEEN :tgBatDau AND :tgKetThuc " +
                "GROUP BY ttsd.thietBi.maTB";
        return entityManager.createQuery(jpql, ThongKeMuonThietBiDto.class)
                .setParameter("tgBatDau", java.sql.Date.valueOf(tgBatDau))
                .setParameter("tgKetThuc", java.sql.Date.valueOf(tgKetThuc))
                .getResultList();
    }

    //Xoá 1 tt theo mã
    public void delete(int id){
        thongTinSdRepository.deleteById(id);
    }
}

