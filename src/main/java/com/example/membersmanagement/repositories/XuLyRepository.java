package com.example.membersmanagement.repositories;

import com.example.membersmanagement.entities.ThanhVienEntity;
import com.example.membersmanagement.entities.ThietBiEntity;
import com.example.membersmanagement.entities.XuLyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface XuLyRepository extends JpaRepository<XuLyEntity, Integer> {
    XuLyEntity findByThanhVienAndTrangThaiXL(ThanhVienEntity thanhVien, int trangThaiXL);

}
