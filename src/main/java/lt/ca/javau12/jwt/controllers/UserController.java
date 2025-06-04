package lt.ca.javau12.jwt.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {

		@GetMapping("/test")
		public String simpleTest() {
			return "This is response from USER endpoint";
		}
	
}
