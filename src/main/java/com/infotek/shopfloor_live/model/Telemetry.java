package com.infotek.shopfloor_live.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "telemetry")
public class Telemetry implements Serializable {

	private static final long serialVersionUID = 1L;    
	
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;

	  @NotNull
	  @Column(name = "machine_id")     // ← mapea snake_case
	  private Long machineId;

	  @NotNull
	  @Column(name = "ts")
	  private Instant ts;

	  @Column(name = "spindle_speed")  // ← mapea snake_case
	  private Integer spindleSpeed;

	  @Column(name = "feed_rate")      // ← mapea snake_case
	  private Integer feedRate;

	  @Column(name = "part_count")     // ← mapea snake_case
	  private Integer partCount;

	  @Column(name = "alarm_code")     // ← AQUÍ el fix principal
	  private String alarmCode;

	  @Column(name = "temperature")
	  private Double temperature;
	
	public Telemetry() {
		super();
	}
	
	public Telemetry(Long id, Long machineId, Instant ts, Integer spindleSpeed, Integer feedRate, Integer partCount,
			String alarmCode, Double temperature) {
		super();
		this.id = id;
		this.machineId = machineId;
		this.ts = ts;
		this.spindleSpeed = spindleSpeed;
		this.feedRate = feedRate;
		this.partCount = partCount;
		this.alarmCode = alarmCode;
		this.temperature = temperature;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMachineId() {
		return machineId;
	}
	public void setMachineId(Long machineId) {
		this.machineId = machineId;
	}
	public Instant getTs() {
		return ts;
	}
	public void setTs(Instant ts) {
		this.ts = ts;
	}
	public Integer getSpindleSpeed() {
		return spindleSpeed;
	}
	public void setSpindleSpeed(Integer spindleSpeed) {
		this.spindleSpeed = spindleSpeed;
	}
	public Integer getFeedRate() {
		return feedRate;
	}
	public void setFeedRate(Integer feedRate) {
		this.feedRate = feedRate;
	}
	public Integer getPartCount() {
		return partCount;
	}
	public void setPartCount(Integer partCount) {
		this.partCount = partCount;
	}
	public String getAlarmCode() {
		return alarmCode;
	}
	public void setAlarmCode(String alarmCode) {
		this.alarmCode = alarmCode;
	}
	public Double getTemperature() {
		return temperature;
	}
	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}
	@Override
	public int hashCode() {
		return Objects.hash(alarmCode, feedRate, id, machineId, partCount, spindleSpeed, temperature, ts);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Telemetry other = (Telemetry) obj;
		return Objects.equals(alarmCode, other.alarmCode) && Objects.equals(feedRate, other.feedRate)
				&& Objects.equals(id, other.id) && Objects.equals(machineId, other.machineId)
				&& Objects.equals(partCount, other.partCount) && Objects.equals(spindleSpeed, other.spindleSpeed)
				&& Objects.equals(temperature, other.temperature) && Objects.equals(ts, other.ts);
	}
	@Override
	public String toString() {
		return "Telemetry [id=" + id + ", machineId=" + machineId + ", ts=" + ts + ", spindleSpeed=" + spindleSpeed
				+ ", feedRate=" + feedRate + ", partCount=" + partCount + ", alarmCode=" + alarmCode + ", temperature="
				+ temperature + "]";
	}
	  
	  


}
