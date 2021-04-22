package myapp.springstarter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import myapp.springstarter.domain.User;
import myapp.springstarter.exceptions.DuplicateUserNameException;

import myapp.springstarter.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public User saveUser(User newUser) {
		try {
			newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));			
			
			//User name has to be unique
			newUser.setUsername(newUser.getUsername());
			newUser.setConfirmPassword("");
			//Make sure that password and confirmPassword match
			//We don't persist or show the confirmPassword
			return userRepository.save(newUser);
		} catch (Exception e) {
			throw new DuplicateUserNameException("Username " + newUser.getUsername() + " is already exist");
		}
		
		
	}
	
}
