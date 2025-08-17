package com.infotek.shopfloor_live.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class TelemetryWsController {
  @MessageMapping("/ping") @SendTo("/topic/telemetry")
  public String ping(String msg) {
	  return "pong:" + msg;
	  }
}
