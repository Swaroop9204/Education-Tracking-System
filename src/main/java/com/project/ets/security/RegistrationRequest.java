package com.project.ets.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {
	
	@NotNull(message = "user_name cannot be null")
	@Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_-]{2,19}$")
	private String username;
	@NotNull(message = "email cannot be null")
	@Email(regexp ="^[a-zA-Z0-9._%+-]+@gmail\\.com$" )
	private String email;
	@NotNull(message = "password cannot be null")
	@Pattern(regexp ="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",message = "password must contain atleast 1 uppercase"
			+ " 1 lower case and special character and min of 8 lengths" )
	private String password;

}
