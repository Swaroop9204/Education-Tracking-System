package com.project.ets.requstdto;

import org.springframework.stereotype.Component;

import com.project.ets.enums.UserRole;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class UserRequest {
	@NotNull(message = "user name should not be null")
	@Pattern(regexp = "^[a-zA-Z0-9#$&*\\-_.!@]+$\r\n",message = "user name should contain alphabets and numbers and special characters are optional")
	private String username;
	@NotNull(message = "email should not be null")
	@Pattern(regexp ="^[a-zA-Z0-9._%+-]+@gmail\\.com$\r\n",message = "email should only end with @gmail.com")
	private String email;
	@NotNull(message = "password should not be null")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$^&*.\\-_])[A-Za-z\\d!@#$^&*.\\-_]{8,}$\r\n",message = "password should contain atleast 8 charactes atleast one"
			+ "upper case atleast 1 lowercase atleast 1 numeric character atleast a special character")
	private String password;
	private UserRole role;
	

}
