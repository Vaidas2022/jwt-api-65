package lt.ca.javau12.jwt;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import lt.ca.javau12.jwt.models.Role;
import lt.ca.javau12.jwt.models.User;
import lt.ca.javau12.jwt.repository.UserRepository;

@Configuration
public class InitConfiguration {

	UserRepository userRepository;
	
	public InitConfiguration(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	//TODO: REMOVE BEFORE PRODUCTION !!!!!!!!!
	@Bean
	CommandLineRunner init(PasswordEncoder encoder) {
		return args -> {
			//(Long id, String username, String password, Role role, boolean enabled)
			if(userRepository.findByUsername("alice").isEmpty()) {
				User user = new User(null, "alice", encoder.encode("pass1234"), Role.ADMIN, true );
				userRepository.save(user);
			}
		};	
	}

	
}
