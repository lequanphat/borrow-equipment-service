package com.example.membersmanagement.repositories;

import com.example.membersmanagement.entities.ThanhVienDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThanhVienRepository extends JpaRepository<ThanhVienDTO, Integer> {
    ThanhVienDTO findByEmail(String email);
    ThanhVienDTO findByMaTV(int maTV);
}
