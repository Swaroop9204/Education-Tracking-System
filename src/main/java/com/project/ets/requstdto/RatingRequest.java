package com.project.ets.requstdto;

import com.project.ets.enums.Subject;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingRequest {
	@NotNull
	@Pattern(regexp = "^[1-5]$",message = "it should be in between 1 to 5")
	private int rating;
	
	private String feedback;

}
