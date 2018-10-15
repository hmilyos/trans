package com.imooc.example.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrderQueryApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderQueryApplication.class, args);
	}
}
