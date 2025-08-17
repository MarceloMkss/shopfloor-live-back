package com.infotek.shopfloor_live.controller;

import java.time.Instant;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infotek.shopfloor_live.model.Telemetry;
import com.infotek.shopfloor_live.model.TelemetryIngestDTO;
import com.infotek.shopfloor_live.repository.TelemetryRepository;
import com.infotek.shopfloor_live.service.KpiService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class TelemetryController {

  private final TelemetryRepository repo;
  private final KpiService kpiService;

  public TelemetryController(TelemetryRepository repo, KpiService kpiService) {
    this.repo = repo; this.kpiService = kpiService;
  }

  @PostMapping("/ingest/telemetry")
  public Telemetry ingest(@Valid @RequestBody TelemetryIngestDTO dto) {
    var t = new Telemetry();
    t.setMachineId(dto.machineId);
    t.setTs(dto.ts);
    t.setSpindleSpeed(dto.spindleSpeed);
    t.setFeedRate(dto.feedRate);
    t.setPartCount(dto.partCount);
    t.setAlarmCode(dto.alarmCode);
    t.setTemperature(dto.temperature);
    return repo.save(t);
  }

  @GetMapping("/kpi/production")
  public Map<String,Object> kpi(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to) {
    return kpiService.productionKpis(from, to);
  }
}