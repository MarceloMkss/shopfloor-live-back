package com.infotek.shopfloor_live.model;

import java.time.Instant;

import jakarta.validation.constraints.NotNull;

public class TelemetryIngestDTO {
	
	  @NotNull public Long machineId;
	  @NotNull public Instant ts;
	  public Integer spindleSpeed;
	  public Integer feedRate;
	  public Integer partCount;
	  public String alarmCode;
	  public Double temperature;

}
