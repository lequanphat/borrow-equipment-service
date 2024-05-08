package com.example.membersmanagement.repositories;

import com.example.membersmanagement.entities.ThanhVienEntity;
import com.example.membersmanagement.entities.ThietBiEntity;
import com.example.membersmanagement.entities.ThongTinSdEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ThongTinSdRepository extends JpaRepository<ThongTinSdEntity, Integer> {
    boolean existsByThietBiMaTBAndTgMuonIsNotNullAndTgTraIsNull(int maTB);

    boolean existsByThietBiMaTBAndTgDatChoIsNotNull(int maTB);

    boolean existsByThietBiMaTBAndTgDatChoIsNotNullAndThanhVienMaTV(int maTB, int maTV);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM ThongTinSdEntity c WHERE c.thietBi.maTB = :maTB AND c.tgDatCho IS NOT NULL AND DATE(c.tgDatCho) = :date")
    boolean existsByThietBiMaTBAndTgDatChoIsNotNullAndTgDatCho(@Param("maTB") int maTB, @Param("date") LocalDate date);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM ThongTinSdEntity c WHERE c.thietBi.maTB = :maTB AND c.tgMuon IS NOT NULL AND c.tgTra IS NULL AND DATE(c.tgMuon) = :date")
    boolean existsByThietBiMaTBAndTgMuonIsNotNullAndTgTraIsNullAndTgMuon(@Param("maTB") int maTB, @Param("date") LocalDate date);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM ThongTinSdEntity c WHERE c.thietBi.maTB = :maTB AND c.tgDatCho IS NOT NULL AND c.thanhVien.maTV != :maTV AND DATE(c.tgDatCho) = :date")
    boolean isBorrowingByAnotherMember(@Param("maTB") int maTB, @Param("maTV") int maTV, @Param("date") LocalDate date);


    ThongTinSdEntity findByThietBiMaTBAndTgMuonIsNotNullAndTgTraIsNull(int maTB);

    Page<ThongTinSdEntity> findByThietBiMaTBAndThanhVienHoTenContainingIgnoreCaseAndTgMuonIsNotNullOrderByTgMuonDesc(int maTB, String hoTen, Pageable paging);

    Page<ThongTinSdEntity> findByThietBiTenTBContainingIgnoreCaseAndTgDatChoIsNotNullAndTgMuonIsNull(String tenTB, Pageable paging);

    List<ThongTinSdEntity> findByThanhVienMaTVAndTgMuonIsNotNullAndTgTraIsNull(int maTV);

    List<ThongTinSdEntity> findByThanhVienMaTVAndTgDatChoIsNotNullAndTgMuonIsNull(int maTV);

    List<ThongTinSdEntity> findByThanhVienMaTVAndTgVaoIsNotNull(int maTV);

    @Query("SELECT ttsd " +
            "FROM ThongTinSdEntity ttsd " +
            "WHERE ttsd.tgMuon IS NOT NULL AND ttsd.tgTra IS NULL AND (ttsd.thietBi.tenTB LIKE  :search OR CAST(ttsd.thietBi.maTB AS string) LIKE :search OR ttsd.thanhVien.hoTen LIKE :search OR CAST(ttsd.thanhVien.maTV AS string) LIKE :search)")
    List<ThongTinSdEntity> getDsThietBiDangDuocMuon(@Param("search") String search);

    void deleteByThanhVienMaTVAndThietBiMaTBAndTgDatChoIsNotNull(int maTV, int maTB);
}
