package com.itrane.spbootdemo.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
//@EnableAutoConfiguration
//@Configuration
@ComponentScan("com.itrane.spbootdemo")
@PropertySource("classpath:/app.properties")
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class);
	}
}