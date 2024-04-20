package com.example.membersmanagement.repositories;

import com.example.membersmanagement.entities.ThanhVienDTO;
import com.example.membersmanagement.entities.XuLyDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface XuLyRepository extends JpaRepository<XuLyDTO, Integer> {
}
