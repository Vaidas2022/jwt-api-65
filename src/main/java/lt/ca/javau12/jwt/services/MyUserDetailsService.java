package lt.ca.javau12.jwt.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lt.ca.javau12.jwt.repository.UserRepository;
import lt.ca.javau12.jwt.models.User;

@Service
public class MyUserDetailsService implements UserDetailsService {

	UserRepository userRepository;
	
	public MyUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByUsername(username)
				.orElseThrow(()-> new UsernameNotFoundException("UserNotFound") );
		
		return org.springframework.security.core.userdetails.User.builder()
				.username( user.getUsername())
				.password( user.getPassword())
				.authorities( "ROLE_" + user.getRole().name()) 
				.disabled( !user.isEnabled())
				.build();

	}

}
