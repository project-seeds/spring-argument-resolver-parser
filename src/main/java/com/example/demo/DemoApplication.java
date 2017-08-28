package com.example.demo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Configuration
	public static class WebConfig extends WebMvcConfigurerAdapter {
		
		@Override
		public void addFormatters(FormatterRegistry registry) {
			super.addFormatters(registry);
			
			DateFormatter javaUtilDateFormatter = new DateFormatter();
			javaUtilDateFormatter.setIso(ISO.DATE_TIME);

			DateTimeFormatter jsr310Formatter = DateTimeFormatter.ISO_DATE_TIME;

			DateFormatterRegistrar javaUtilDate = new DateFormatterRegistrar();
			javaUtilDate.setFormatter(javaUtilDateFormatter); // whatever format
			javaUtilDate.registerFormatters(registry);

			DateTimeFormatterRegistrar jsr310 = new DateTimeFormatterRegistrar();
			jsr310.setDateTimeFormatter(jsr310Formatter);
			jsr310.setDateFormatter(jsr310Formatter);
			jsr310.setTimeFormatter(jsr310Formatter);
			jsr310.registerFormatters(registry);
			
		}
	}

	@RestController
	@RequestMapping("/")
	public static class Api {

		@GetMapping
		public ResponseEntity<?> get(
				@RequestParam("dateTime") LocalDateTime dateTime) {
			return ResponseEntity.ok(dateTime);
		}

	}
}
