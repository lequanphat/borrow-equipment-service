package com.example.membersmanagement.repositories;

import com.example.membersmanagement.entities.ThietBiEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThietBiRepository extends JpaRepository<ThietBiEntity, Integer> {
    public Page<ThietBiEntity> findByTenTBContainingIgnoreCase(String tenTB, Pageable paging);
}
