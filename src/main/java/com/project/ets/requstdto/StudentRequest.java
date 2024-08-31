package com.project.ets.requstdto;

import java.time.Year;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentRequest extends UserRequest{
	
	@NotNull(message = "should not be null")
	@Pattern(regexp = "^[a-zA-Z]+$",message = "should enter only alphabets")
	private String degree;
	@NotNull(message = "should not be null")
	@Pattern(regexp = "^[a-zA-Z]+$",message = "should enter only alphabets")
	private String stream;
	@NotNull(message = "should not be null")
	@Pattern(regexp = "^\\d{4}$",message = "should enter only numbers upto 4 digits")
	private Year yearOfPassout;
	private int degreePercentage;
	private int tenthPercentage;
	private int twelthPercentage;
	

}
