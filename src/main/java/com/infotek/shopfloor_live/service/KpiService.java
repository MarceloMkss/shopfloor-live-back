package com.infotek.shopfloor_live.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.infotek.shopfloor_live.model.Telemetry;
import com.infotek.shopfloor_live.repository.MachineRepository;
import com.infotek.shopfloor_live.repository.TelemetryRepository;

@Service
public class KpiService {

	private final TelemetryRepository teleRepo;
	private final MachineRepository machineRepo;

	public KpiService(TelemetryRepository t, MachineRepository m) {
		this.teleRepo = t;
		this.machineRepo = m;
	}

	public Map<String, Object> productionKpis(Instant from, Instant to) {

		var telemetries = teleRepo.findByTsBetweenOrderByTsAsc(from, to);

		// Producción por máquina (sum partCount último - primero)
		var byMachine = telemetries.stream()
				.collect(Collectors.groupingBy(Telemetry::getMachineId, LinkedHashMap::new, Collectors.toList()));

		var prodPerMachine = new ArrayList<Map<String, Object>>();
		byMachine.forEach((machineId, list) -> {
			// si partCount es acumulado, aproximamos producción en el rango:
			var first = list.get(0).getPartCount() == null ? 0 : list.get(0).getPartCount();
			var last = list.get(list.size() - 1).getPartCount() == null ? first
					: list.get(list.size() - 1).getPartCount();
			var prod = Math.max(0, last - first);
			var name = machineRepo.findById(machineId).map(m -> m.getName()).orElse("M-" + machineId);
			prodPerMachine.add(Map.of("machineId", machineId, "machine", name, "qty", prod));
		});

		// Alarmas por código
		var alarms = telemetries.stream().filter(t -> t.getAlarmCode() != null && !t.getAlarmCode().isBlank())
				.collect(Collectors.groupingBy(Telemetry::getAlarmCode, Collectors.counting()));

		// Temperatura media global (demo)
		var tempAvg = telemetries.stream().filter(t -> t.getTemperature() != null)
				.mapToDouble(Telemetry::getTemperature).average().orElse(0);

		return Map.of("from", from, "to", to, "productionByMachine", prodPerMachine, "alarmsByCode", alarms,
				"avgTemperature", tempAvg);
	}
}
