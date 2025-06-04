package lt.ca.javau12.jwt.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lt.ca.javau12.jwt.dto.LoginRequest;
import lt.ca.javau12.jwt.dto.LoginResponse;
import lt.ca.javau12.jwt.dto.SignupRequest;
import lt.ca.javau12.jwt.models.Role;
import lt.ca.javau12.jwt.models.User;
import lt.ca.javau12.jwt.repository.UserRepository;
import lt.ca.javau12.jwt.security.JwtUtils;
import lt.ca.javau12.jwt.services.MyUserDetailsService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final UserRepository userRepo;
	
	private final AuthenticationManager authenticationManager;
	
	private final PasswordEncoder passwordEncoder;
	
	private final JwtUtils jwtUtils;
	
	public AuthController(UserRepository userRepo, 
			AuthenticationManager authenticationManager,
			PasswordEncoder passwordEncoder,
			JwtUtils jwtUtils) {
		this.userRepo = userRepo;
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtils = jwtUtils;
	}
	
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody SignupRequest request){
		User user = new User(null, request.username(), passwordEncoder.encode(request.password()), Role.USER, true);
		userRepo.save(user);
		
		return ResponseEntity.ok("Registered successfully");
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request){
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.username(), request.password())
	        );
	        String jwt = jwtUtils.generateToken(request.username());

	        return ResponseEntity.ok(new LoginResponse(jwt));
	}
	
	
	
	
}
