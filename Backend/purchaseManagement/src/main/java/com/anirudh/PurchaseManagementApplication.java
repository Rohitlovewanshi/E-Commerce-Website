package com.anirudh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.anirudh.entity.Product;
import com.anirudh.entity.ProductCategories;
import com.anirudh.vo.Order;

@SpringBootApplication
@EnableEurekaClient
public class PurchaseManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(PurchaseManagementApplication.class, args);
	}
	@Bean
	public Order getOrder() {
		return new Order();
	}
	@Bean
	public Product getProduct() {
		return new Product();
	}
	@Bean
	public ProductCategories getProductCategories() {
		return new ProductCategories();
	}

}
