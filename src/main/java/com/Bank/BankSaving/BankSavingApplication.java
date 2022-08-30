package com.Bank.BankSaving;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class BankSavingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankSavingApplication.class, args);
	}

}
