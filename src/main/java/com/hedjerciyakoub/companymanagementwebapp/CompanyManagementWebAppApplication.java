package com.hedjerciyakoub.companymanagementwebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@EnableAutoConfiguration
@SpringBootApplication
public class CompanyManagementWebAppApplication {

	public static void main(String[] args) {

		SpringApplication.run(CompanyManagementWebAppApplication.class, args );

	}

}
	  