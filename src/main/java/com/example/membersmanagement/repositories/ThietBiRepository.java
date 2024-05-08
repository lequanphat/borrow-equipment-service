package com.example.membersmanagement.repositories;

import com.example.membersmanagement.entities.ThietBiEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ThietBiRepository extends JpaRepository<ThietBiEntity, Integer> {
    public Page<ThietBiEntity> findByTenTBContainingIgnoreCase(String tenTB, Pageable paging);
    @Modifying
    @Query("DELETE FROM ThietBiEntity WHERE SUBSTRING(CONCAT('', maTB), 1, 1) = :maLoai")
    void multipleDeleteByMaLoai(@Param("maLoai") String maLoai);
}
