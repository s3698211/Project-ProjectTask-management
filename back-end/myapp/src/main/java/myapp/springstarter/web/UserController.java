package myapp.springstarter.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import myapp.springstarter.domain.User;
import myapp.springstarter.payload.JWTLoginSuccessReponse;
import myapp.springstarter.payload.LoginRequest;
import myapp.springstarter.security.JwtTokenProvider;
import myapp.springstarter.services.MapValidationErrorService;
import myapp.springstarter.services.UserService;
import myapp.springstarter.validator.UserValidator;
import static myapp.springstarter.security.SecurityConstants.TOKEN_PREFIX;;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateuser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
		
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if(errorMap != null) return errorMap;
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginRequest.getUsername(), 
				loginRequest.getPassword()
				)
		);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = TOKEN_PREFIX + tokenProvider.generateToken(authentication);
		
		return ResponseEntity.ok(new JWTLoginSuccessReponse(true, jwt));
	}
	
	@PostMapping("/register")
	public ResponseEntity<?>registerUser(@Valid @RequestBody User user, BindingResult result) {
		//Validate passwords match
		userValidator.validate(user, result);
		
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if(errorMap != null) return errorMap;
		
		User newUser = userService.saveUser(user);
		
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
		
	}
}
