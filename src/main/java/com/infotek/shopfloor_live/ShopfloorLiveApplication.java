package com.infotek.shopfloor_live;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ShopfloorLiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopfloorLiveApplication.class, args);
	}

}
