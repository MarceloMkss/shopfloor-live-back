package com.infotek.shopfloor_live.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.infotek.shopfloor_live.model.Machine;

public interface MachineRepository extends JpaRepository<Machine, Long> {

	Page<Machine> findByStatusIgnoreCase(String status, Pageable pageable);
}
