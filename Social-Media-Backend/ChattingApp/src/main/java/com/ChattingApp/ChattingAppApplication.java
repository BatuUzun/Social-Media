package com.ChattingApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(exclude = {
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class })

@EntityScan(basePackages = { "com.ChattingApp.Entity" })
public class ChattingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChattingAppApplication.class, args);
	}

}
