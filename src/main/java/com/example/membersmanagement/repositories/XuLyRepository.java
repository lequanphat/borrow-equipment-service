package com.example.membersmanagement.repositories;

import com.example.membersmanagement.entities.ThanhVienEntity;
import com.example.membersmanagement.entities.ThietBiEntity;
import com.example.membersmanagement.entities.XuLyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface XuLyRepository extends JpaRepository<XuLyEntity, Integer> {
    List<XuLyEntity> findByThanhVienMaTVOrderByNgayXLDesc(int maTV);

    XuLyEntity findByThanhVienAndTrangThaiXL(ThanhVienEntity thanhVien, int trangThaiXL);

    @Query("SELECT SUM(xl.soTien) FROM XuLyEntity xl WHERE xl IN :list")
    int sumTongTien(List<XuLyEntity> list);
}
