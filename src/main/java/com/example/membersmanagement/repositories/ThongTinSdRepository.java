package com.example.membersmanagement.repositories;

import com.example.membersmanagement.entities.ThanhVienDTO;
import com.example.membersmanagement.entities.ThongTinSdDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThongTinSdRepository extends JpaRepository<ThongTinSdDTO, Integer> {
}
