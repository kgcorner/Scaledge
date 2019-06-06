package com.kgcorner.scaledgecontent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.kgcorner")
public class ScaledgeContentApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScaledgeContentApplication.class, args);
	}

}

