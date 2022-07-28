package com.parceldelivery.parceleurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ParcelEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParcelEurekaServerApplication.class, args);
	}

}
