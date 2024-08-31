package com.project.ets.responsedto;

import java.time.Year;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentResponse extends UserResponse{
	
	private String degree;
	private String stream;
	private Year   yearOfPassout;
	private double degreePercentage;
	private double tenthPercentage;
	private double twelvethPercentage;

}
