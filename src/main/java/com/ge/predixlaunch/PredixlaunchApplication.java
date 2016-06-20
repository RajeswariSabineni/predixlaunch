package com.ge.predixlaunch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@SpringBootApplication
@Configuration
@Import({com.ge.predixlaunch.config.WebSecurityConfig.class})
public class PredixlaunchApplication {

	public static void main(String[] args) {
		SpringApplication.run(PredixlaunchApplication.class, args);
	}
}
