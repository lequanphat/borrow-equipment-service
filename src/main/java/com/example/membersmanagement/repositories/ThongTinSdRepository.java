package com.example.membersmanagement.repositories;

import com.example.membersmanagement.entities.ThongTinSdEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThongTinSdRepository extends JpaRepository<ThongTinSdEntity, Integer> {

    List<ThongTinSdEntity> findByTgMuonIsNotNull();

    // Kiểm tra thiết bị có đang được mượn hoặc đặt chỗ
    boolean existsByThietBiMaTBAndTgMuonIsNotNullAndTgTraIsNull(int maTB);
    boolean existsByThietBiMaTBAndTgDatChoIsNotNull(int maTB);

    ThongTinSdEntity findByThietBiMaTBAndTgMuonIsNotNullAndTgTraIsNull(int maTB);
}
