package com.waly.walyCatalog;

import com.waly.walyCatalog.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class WalyCatalogApplication{


		public static void main(String[] args) {
		SpringApplication.run(WalyCatalogApplication.class, args);
	}

}
