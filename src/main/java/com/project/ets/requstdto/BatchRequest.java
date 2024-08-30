package com.project.ets.requstdto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.project.ets.entity.Student;
import com.project.ets.enums.Subject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatchRequest {
	
	private String title;
	private List<Subject> subjects;
	private LocalDate closedDate;
	private List<Student> students;
	

}
