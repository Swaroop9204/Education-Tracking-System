package com.project.ets.responsedto;

import java.util.List;

import com.project.ets.enums.Subject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainerResponse extends UserResponse{
	
	private List<Subject> subjects;

}
