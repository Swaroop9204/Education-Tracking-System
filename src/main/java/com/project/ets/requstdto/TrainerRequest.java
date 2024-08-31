package com.project.ets.requstdto;

import java.util.List;

import com.project.ets.enums.Subject;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TrainerRequest extends UserRequest{
	@NotNull
	private List<Subject> subjects;

}
