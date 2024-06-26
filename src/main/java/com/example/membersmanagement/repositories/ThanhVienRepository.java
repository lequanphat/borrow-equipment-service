package com.example.membersmanagement.repositories;

import com.example.membersmanagement.entities.ThanhVienEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThanhVienRepository extends JpaRepository<ThanhVienEntity, Integer> {
    ThanhVienEntity findByEmail(String email);

    ThanhVienEntity findByMaTV(int maTV);

    boolean existsByMaTV(int maTV);

    List<ThanhVienEntity> findAll();

    @Query("SELECT tv FROM ThanhVienEntity tv WHERE tv.hoTen LIKE %:searchText% OR cast(tv.maTV as STRING) LIKE %:searchText% OR tv.email LIKE %:searchText% OR tv.sdt LIKE %:searchText%")
    public Page<ThanhVienEntity> findBySearchText(@Param("searchText") String searchText, Pageable paging);

    @Modifying
    @Query("DELETE FROM ThanhVienEntity WHERE SUBSTRING(CONCAT('', maTV), 2, 2) = :khoa")
    void multipleDeleteByKhoa(@Param("khoa") String khoa);
}
