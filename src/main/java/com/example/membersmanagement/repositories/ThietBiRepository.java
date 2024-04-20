package com.example.membersmanagement.repositories;

import com.example.membersmanagement.entities.ThanhVienDTO;
import com.example.membersmanagement.entities.ThietBiDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThietBiRepository extends JpaRepository<ThietBiDTO, Integer> {
}
