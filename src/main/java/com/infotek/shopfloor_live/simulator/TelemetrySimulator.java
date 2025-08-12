package com.infotek.shopfloor_live.simulator;

import java.time.Instant;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.infotek.shopfloor_live.dto.TelemetryEvent;
import com.infotek.shopfloor_live.model.Telemetry;
import com.infotek.shopfloor_live.repository.MachineRepository;
import com.infotek.shopfloor_live.repository.TelemetryRepository;

@Service
public class TelemetrySimulator {
	
  private static final Logger log = LoggerFactory.getLogger(TelemetrySimulator.class);
  
  private final TelemetryRepository teleRepo;
  private final MachineRepository machineRepo;
  private final Random rnd = new Random();
  private final SimpMessagingTemplate broker;

  // partCount acumulado por máquina
  private final Map<Long, AtomicInteger> counters = new ConcurrentHashMap<>();

	public TelemetrySimulator(TelemetryRepository teleRepo, MachineRepository machineRepo, SimpMessagingTemplate broker) {
		this.teleRepo = teleRepo;
		this.machineRepo = machineRepo;
		this.broker = broker;
	}

  // cada 5 segundos
	@Scheduled(fixedRate = 5000, initialDelay = 3000)
	public void tick() {
	  try {
	    // 1) Cargar máquinas una vez
	    var machines = machineRepo.findAll();
	    // 2) Si no hay, salir sin hacer nada
		if (machines.isEmpty()) {
			log.info("[TELEMETRY] No hay máquinas registradas, no se simula telemetría.");
			return;
		}

	    // 3) Simular por cada máquina
	    machines.forEach(machine -> {
	      var id = machine.getId();
	      counters.putIfAbsent(id, new AtomicInteger(0));
	      var parts = counters.get(id);

	      int delta = rnd.nextInt(4);
	      int newCount = parts.addAndGet(delta);

	      Telemetry t = new Telemetry();
	      t.setMachineId(id);
	      t.setTs(Instant.now());
	      t.setSpindleSpeed(1500 + rnd.nextInt(1500));
	      t.setFeedRate(1200 + rnd.nextInt(900));
	      t.setPartCount(newCount);
	      t.setTemperature(35 + rnd.nextDouble() * 10);
	      t.setAlarmCode(rnd.nextInt(10) == 0 ? (rnd.nextBoolean() ? "A101" : "OVERHEAT") : null);

	      teleRepo.save(t);

	      broker.convertAndSend(
	        "/topic/telemetry",
	        new TelemetryEvent(
	          id,
	          t.getTs().toString(),
	          newCount,
	          t.getSpindleSpeed(),
	          t.getFeedRate(),
	          t.getAlarmCode() == null ? "" : t.getAlarmCode(),
	          t.getTemperature()
	        )
	      );
	    });

	  } catch (Exception e) {
	    System.err.println("[ERROR] Fallo en simulación de telemetría: " + e.getMessage());
	    e.printStackTrace();
	  }
	}

}