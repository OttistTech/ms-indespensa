package com.ottistech.indespensa.api.ms_indespensa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsIndespensaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsIndespensaApplication.class, args);
	}

}
