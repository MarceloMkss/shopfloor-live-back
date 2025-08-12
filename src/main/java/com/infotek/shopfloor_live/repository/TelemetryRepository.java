package com.infotek.shopfloor_live.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infotek.shopfloor_live.model.Telemetry;

public interface TelemetryRepository extends JpaRepository<Telemetry, Long> {
	
	List<Telemetry> findByMachineIdAndTsBetweenOrderByTsAsc(Long machineId, Instant from, Instant to);
	List<Telemetry> findByTsBetweenOrderByTsAsc(Instant from, Instant to);

}
