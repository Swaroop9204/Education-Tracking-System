package com.project.ets.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.ets.enums.Stack;
import com.project.ets.enums.UserRole;
import com.project.ets.requstdto.StudentRequest;
import com.project.ets.requstdto.TrainerRequest;
import com.project.ets.responsedto.RatingResponse;
import com.project.ets.responsedto.StudentResponse;
import com.project.ets.responsedto.UserResponse;
import com.project.ets.security.RegistrationRequest;
import com.project.ets.service.UserService;
import com.project.ets.util.AppResponseBuilder;
import com.project.ets.util.ResponseStructure;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {
	private UserService userService;
	private AppResponseBuilder responseBuilder;
	
	@PostMapping("/admins")
	public ResponseEntity<ResponseStructure<UserResponse>> saveAdmin(@RequestBody @Valid RegistrationRequest registrationRequest) {
		UserResponse adminResponse=userService.saveUser(registrationRequest,UserRole.ADMIN);
		return responseBuilder.success(HttpStatus.CREATED,"Admin created Successfully", adminResponse);
	}
	
	@PostMapping("/hrs")
	public ResponseEntity<ResponseStructure<UserResponse>>saveHr(@RequestBody @Valid RegistrationRequest registrationRequest){
		UserResponse hrResponse=userService.saveUser(registrationRequest,UserRole.HR);
		return responseBuilder.success(HttpStatus.CREATED, "Hr created Successfully", hrResponse);
	}
	
	@PostMapping("/trainers")
	public ResponseEntity<ResponseStructure<UserResponse>> saveTrainer(@RequestBody @Valid RegistrationRequest registrationRequest){
		UserResponse response=userService.saveUser(registrationRequest,UserRole.TRAINER);
		return responseBuilder.success(HttpStatus.CREATED, "Trainer created successfully", response);
	}
	
	@PutMapping("/trainers/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> updateTrainer(@RequestBody @Valid TrainerRequest trainerRequest,@PathVariable String userId){
		UserResponse response=userService.updateTrainer(trainerRequest,userId);
		return responseBuilder.success(HttpStatus.OK, "Trainer updated", response);
	}
	
	@PostMapping("/students")
	public ResponseEntity<ResponseStructure<UserResponse>> saveStudent(@RequestBody @Valid RegistrationRequest registrationRequest){
		UserResponse response=userService.saveUser(registrationRequest,UserRole.STUDENT);
		return responseBuilder.success(HttpStatus.CREATED, "Student created successfully", response);
	}
	
	@PutMapping("/students/{userId}")
	public ResponseEntity<ResponseStructure<StudentResponse>> updateStudent(@RequestBody @Valid StudentRequest studentRequest,@PathVariable String userId){
		StudentResponse studentResponse=userService.updateStudent(studentRequest,userId);
		return responseBuilder.success(HttpStatus.OK, "Student Updated", studentResponse);
	}
	
	@PatchMapping("/students/{userId}")
	public ResponseEntity<ResponseStructure<StudentResponse>> updateStudentStack(@RequestParam Stack stack,@PathVariable String userId){
		StudentResponse response=userService.updateStudent(stack, userId);
		return responseBuilder.success(HttpStatus.OK, "Student stack is Updated", response);
	}
	
	@GetMapping("/students/{studentId}")
	public ResponseEntity<ResponseStructure<List<RatingResponse>>> viewRating(@PathVariable String studentId){
		List<RatingResponse> responses=userService.viewRating(studentId);
		return responseBuilder.success(HttpStatus.FOUND, "found the ratings of the student", responses);
	}

}
