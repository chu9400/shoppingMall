package com.hanul.shoppingMall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-variable.properties")
public class ShoppingMallApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingMallApplication.class, args);
	}

}
