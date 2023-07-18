package com.waly.walyCatalog;

import com.waly.walyCatalog.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class WalyCatalogApplication implements CommandLineRunner{

	@Autowired
	private S3Service s3Service;
		public static void main(String[] args) {
		SpringApplication.run(WalyCatalogApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		s3Service.uploadFile("C:\\Users\\walys\\Pictures\\Screenshots\\print.jpg");
	}
}
