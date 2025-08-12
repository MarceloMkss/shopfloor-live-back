package com.infotek.shopfloor_live.dto;

public record TelemetryEvent(Long machineId, String ts, Integer partCount,
        Integer spindleSpeed, Integer feedRate,
        String alarmCode, Double temperature) {}

