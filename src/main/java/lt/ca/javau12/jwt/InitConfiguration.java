package lt.ca.javau12.jwt;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lt.ca.javau12.jwt.repository.UserRepository;
import models.Role;
import models.User;

@Configuration
public class InitConfiguration {

	UserRepository userRepository;
	
	public InitConfiguration(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Bean
	CommandLineRunner init() {
		return args -> {
			//(Long id, String username, String password, Role role, boolean enabled)
			if(userRepository.findByUserName("alice").isEmpty()) {
				User user = new User(null, "alice", "pass1234", Role.ADMIN, true );
				userRepository.save(user);
			}
		};	
	}

	
}
