package com.cnsseftstudy.kma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class KmaApplication {

	public static void main(String[] args) {
		SpringApplication.run(KmaApplication.class, args);
	}

}
