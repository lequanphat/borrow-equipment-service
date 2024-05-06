package com.example.membersmanagement.repositories;

import com.example.membersmanagement.entities.ThanhVienEntity;
import com.example.membersmanagement.entities.XuLyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface XuLyRepository extends JpaRepository<XuLyEntity, Integer> {
    XuLyEntity findByThanhVienAndTrangThaiXL(ThanhVienEntity thanhVien, int trangThaiXL);


}
