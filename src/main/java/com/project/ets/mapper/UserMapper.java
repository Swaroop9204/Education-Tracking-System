package com.project.ets.mapper;

import org.springframework.stereotype.Component;

import com.project.ets.entity.Student;
import com.project.ets.entity.Trainer;
import com.project.ets.entity.User;
import com.project.ets.requstdto.StudentRequest;
import com.project.ets.requstdto.TrainerRequest;
import com.project.ets.responsedto.StudentResponse;
import com.project.ets.responsedto.TrainerResponse;
import com.project.ets.responsedto.UserResponse;
import com.project.ets.security.RegistrationRequest;

@Component
public class UserMapper {
public User mapToUserEntity(RegistrationRequest registrationRequest, User user) {
		
		user.setUsername(registrationRequest.getUsername());
		user.setEmail(registrationRequest.getEmail());
		user.setPassword(registrationRequest.getPassword());
		return user;
	}
	
	public UserResponse mapToUserResponse(User user) {
		UserResponse userResponse=new UserResponse();
		userResponse.setUsername(user.getUsername());
		userResponse.setEmail(user.getEmail());
		userResponse.setRole(user.getRole());
		userResponse.setCreatedDate(user.getCreatedDate());
		userResponse.setModifiedDate(user.getModifiedDate());
		return userResponse;
	}
	public Trainer mapToTrainerEntity(TrainerRequest trainerRequest,Trainer trainer) {
		if(trainerRequest.getUsername()!=null)
		trainer.setUsername(trainerRequest.getUsername());
		if(trainerRequest.getEmail()!=null)
		trainer.setEmail(trainerRequest.getEmail());
		trainer.setSubjects(trainerRequest.getSubjects());
		return trainer;
	}
	public TrainerResponse mapToTrainerResponse(Trainer trainer) {
		TrainerResponse response=new TrainerResponse();
		response.setUsername(trainer.getUsername());
		response.setEmail(trainer.getEmail());
		response.setSubjects(trainer.getSubjects());
		response.setModifiedDate(trainer.getModifiedDate());
		return response;
	}
	public Student mapToStudentEntity(StudentRequest studentRequest, Student student) {
		if(studentRequest!=null)
		student.setUsername(studentRequest.getUsername());
		if(studentRequest!=null)
		student.setEmail(studentRequest.getEmail());
		student.setDegree(studentRequest.getDegree());
		student.setStream(studentRequest.getStream());
		student.setYearOfPassout(studentRequest.getYearOfPassout());
		student.setDegreePercentage(studentRequest.getDegreePercentage());
		student.setTwelthPercentage(studentRequest.getTwelthPercentage());
		student.setTenthPercentage(studentRequest.getTenthPercentage());
		return student;
	}
	public StudentResponse mapToStudentResponse(Student student) {
		StudentResponse studentResponse=new StudentResponse();
		studentResponse.setUsername(student.getUsername());
		studentResponse.setEmail(student.getEmail());
		studentResponse.setDegree(student.getDegree());
		studentResponse.setStream(student.getStream());
		studentResponse.setDegree(student.getDegree());
		studentResponse.setYearOfPassout(student.getYearOfPassout());
		studentResponse.setDegreePercentage(student.getDegreePercentage());
		studentResponse.setTwelvethPercentage(student.getTwelthPercentage());
		studentResponse.setTenthPercentage(student.getTenthPercentage());
		return studentResponse;
	}

}
