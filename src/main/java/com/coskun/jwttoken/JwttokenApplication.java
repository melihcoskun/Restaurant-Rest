package com.coskun.jwttoken;

import com.coskun.jwttoken.entity.Role;
import com.coskun.jwttoken.entity.User;
import com.coskun.jwttoken.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class JwttokenApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwttokenApplication.class, args);
	}

/*	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		System.out.println("hello world, I have just started up");



	} */

	@Bean
	public CommandLineRunner demo(UserRepository repository,
								  PasswordEncoder passwordEncoder) {
		return (args) -> {

			User user = User.builder()
					.firstName("superadmin")
					.lastName("superadmin")
					.email("superadmin@gmail.com")
					.password(passwordEncoder.encode("mmogluk57"))
					.role(Role.SUPERADMIN)
					.build();

			repository.save(user);
		};
	}

}
