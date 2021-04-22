package myapp.springstarter.validator;



import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import myapp.springstarter.domain.User;

@Component
public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> aClass) {
		// TODO Auto-generated method stub
		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object object, Errors errors) {
		
		User user = (User) object;
		if(user.getPassword().length() < 6) {
			errors.rejectValue("password", "Length", "Password must be at leaset 6 characters");
			
		}
		
		if(!user.getPassword().equals(user.getConfirmPassword())) {
			errors.rejectValue("confirmPassword", "Length", "Password must match");
			
		}
		
//		confirmPassword
		
	}

	

	
	
 
}
