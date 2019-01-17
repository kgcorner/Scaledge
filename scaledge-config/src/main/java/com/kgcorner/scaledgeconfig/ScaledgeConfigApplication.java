package com.kgcorner.scaledgeconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
@EnableDiscoveryClient
public class ScaledgeConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScaledgeConfigApplication.class, args);
	}

}

