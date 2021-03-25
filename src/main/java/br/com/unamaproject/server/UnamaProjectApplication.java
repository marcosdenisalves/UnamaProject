package br.com.unamaproject.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UnamaProjectApplication implements CommandLineRunner{

	
	public static void main(String[] args) {
		SpringApplication.run(UnamaProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
