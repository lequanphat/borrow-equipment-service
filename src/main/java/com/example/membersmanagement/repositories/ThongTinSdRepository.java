package com.example.membersmanagement.repositories;

import com.example.membersmanagement.entities.ThanhVienEntity;
import com.example.membersmanagement.entities.ThietBiEntity;
import com.example.membersmanagement.entities.ThongTinSdEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ThongTinSdRepository extends JpaRepository<ThongTinSdEntity, Integer> {
    List<ThongTinSdEntity> findByTgMuonIsNotNull();

    boolean existsByThietBiMaTBAndTgMuonIsNotNullAndTgTraIsNull(int maTB);

    boolean existsByThietBiMaTBAndTgDatChoIsNotNull(int maTB);

    ThongTinSdEntity findByThietBiMaTBAndTgMuonIsNotNullAndTgTraIsNull(int maTB);

    Page<ThongTinSdEntity> findByThietBiMaTBAndThanhVienHoTenContainingIgnoreCaseAndTgMuonIsNotNullOrderByTgMuonDesc(int maTB, String hoTen, Pageable paging);

    Page<ThongTinSdEntity> findByThietBiTenTBContainingIgnoreCaseAndTgDatChoIsNotNullAndTgMuonIsNull(String tenTB, Pageable paging);

    List<ThongTinSdEntity> findByThanhVienMaTVAndTgMuonIsNotNullAndTgTraIsNull(int maTV);

    List<ThongTinSdEntity> findByThanhVienMaTVAndTgDatChoIsNotNullAndTgMuonIsNull(int maTV);

    List<ThongTinSdEntity> findByThanhVienMaTVAndTgVaoIsNotNull(int maTV);
}
