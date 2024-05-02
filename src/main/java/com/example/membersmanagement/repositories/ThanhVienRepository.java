package com.example.membersmanagement.repositories;

import com.example.membersmanagement.entities.ThanhVienEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThanhVienRepository extends JpaRepository<ThanhVienEntity, Integer> {
    ThanhVienEntity findByEmail(String email);

    ThanhVienEntity findByMaTV(int maTV);

    boolean existsByMaTV(int maTV);

    List<ThanhVienEntity> findAll();
}
