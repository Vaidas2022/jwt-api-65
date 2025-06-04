package lt.ca.javau12.jwt.configuration;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import lt.ca.javau12.jwt.models.Role;
import lt.ca.javau12.jwt.models.User;
import lt.ca.javau12.jwt.repository.UserRepository;
import lt.ca.javau12.jwt.security.JwtUtils;

@Configuration
public class InitConfiguration {
	
	private final JwtUtils jwtUtils;
	private final UserRepository userRepository;
	
	private static final Logger log = LoggerFactory.getLogger(InitConfiguration.class);

	
	public InitConfiguration(UserRepository userRepository, JwtUtils jwtUtils) {
		this.userRepository = userRepository;
		this.jwtUtils = jwtUtils;
	}
	
	
	//TODO: REMOVE BEFORE PRODUCTION !!!!!!!!!
	@Bean
	CommandLineRunner init(PasswordEncoder encoder) {
		return args -> {
			
			Optional<User> userBox = userRepository.findByUsername("alice");
			//(Long id, String username, String password, Role role, boolean enabled)
			if(userBox.isEmpty()) {
				User user = new User(null, "alice", encoder.encode("pass1234"), Role.ADMIN, true );
				userRepository.save(user);
			} else {
				String username = userBox.get().getUsername();
				String token = jwtUtils.generateToken(username);
				log.info(token);
				String freshToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGljZSIsImlhdCI6MTc0ODk0OTk4OSwiZXhwIjoxNzQ5MDM2Mzg5fQ.hoHEFphovXbKRkt6dsx_hDQzH9JIUKu-sQI1-6KceY8";
			}
		};	
	}

	
}
