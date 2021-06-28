package com.rohit.service.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class RohitServiceRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(RohitServiceRegistryApplication.class, args);
	}

}
