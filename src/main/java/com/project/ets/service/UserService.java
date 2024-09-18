package com.project.ets.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.project.ets.exception.InvalidOtpException;
import com.project.ets.exception.RegistrationSessionExpiredexception;
import com.project.ets.requstdto.LoginRequest;
import com.project.ets.requstdto.OtpRequest;
import com.project.ets.security.JWT_Service;
import com.project.ets.util.CacheHelper;
import com.project.ets.util.MailSenderService;
import com.project.ets.util.MessageModel;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.ets.entity.Admin;
import com.project.ets.entity.HR;
import com.project.ets.entity.Rating;
import com.project.ets.entity.Student;
import com.project.ets.entity.Trainer;
import com.project.ets.entity.User;
import com.project.ets.enums.Stack;
import com.project.ets.enums.UserRole;
import com.project.ets.exception.UserNotFoundByIdException;
import com.project.ets.mapper.RatingMapper;
import com.project.ets.mapper.UserMapper;
import com.project.ets.repository.RatingRepository;
import com.project.ets.repository.UserRepository;
import com.project.ets.requstdto.StudentRequest;
import com.project.ets.requstdto.TrainerRequest;
import com.project.ets.responsedto.RatingResponse;
import com.project.ets.responsedto.StudentResponse;
import com.project.ets.responsedto.UserResponse;
import com.project.ets.security.RegistrationRequest;

import lombok.AllArgsConstructor;

import javax.lang.model.element.Element;


@Service
@AllArgsConstructor
@Slf4j
public class UserService {
	private UserRepository userRepository;
	private UserMapper mapper;
	private RatingRepository ratingRepository;
	private RatingMapper ratingMapper;
	private MailSenderService otpMailSender;
	private Random random;
	private CacheHelper cacheHelper;
	private AuthenticationManager authenticationManager;
	private JWT_Service jwtService;

	public UserResponse saveUser(RegistrationRequest registrationRequest,UserRole role)  {
		User user = null;
		switch (role) {
		case ADMIN -> user = new Admin();
		case HR -> user = new HR();
		case STUDENT -> user = new Student();
		case TRAINER -> user = new Trainer();
		default -> throw new IllegalArgumentException("Unexpected value: " + role);
		}
		if(user != null) {
			user = mapper.mapToUserEntity(registrationRequest, user);
			user.setRole(role);
			int otp=random.nextInt(100000,999999);
			cacheHelper.cacheUser(user);
			cacheHelper.cacheOtp(otp,user.getEmail());
			sendVerificationOtpToUsers(user.getEmail(),otp);
		}
		return mapper.mapToUserResponse(user);
	}

	public UserResponse updateTrainer(TrainerRequest trainerRequest,String userId) {		
		return userRepository.findById(userId).map((user)->{
			user=mapper.mapToTrainerEntity(trainerRequest,(Trainer) user);
			user=userRepository.save(user);
			return mapper.mapToUserResponse(user);
		}).orElseThrow(()->new UserNotFoundByIdException("failed to update the trainer"));
	}

	public StudentResponse updateStudent(StudentRequest studentRequest, String userId) {
		return userRepository.findById(userId).map((user)->{
			user=mapper.mapToStudentEntity(studentRequest,(Student) user);
			user=userRepository.save(user);
			return mapper.mapToStudentResponse((Student)user);
		}).orElseThrow(()->new UserNotFoundByIdException("failed to update the student"));
	}

	public StudentResponse updateStudent(Stack stack, String userId) {
		return	userRepository.findById(userId).map(user->{
			Student student=(Student)user;
			stack.getSubjects().forEach(subject->{
				Rating rating = new Rating();
				rating.setSubject(subject);
				rating.setStudent(student);
				rating=ratingRepository.save(rating);
			});
			student.setStack(stack);
			user=userRepository.save(student);
			return mapper.mapToStudentResponse(student);
		}).orElseThrow(()->new UserNotFoundByIdException("faied to update stack to the student"));
	}

	public List<RatingResponse> viewRating(String userId) {
		return userRepository.findById(userId).map(user->{
			Student student=(Student)user;
			return student.getRatings()
					.stream()
					.map(rating->ratingMapper.mapToRatingResponseEntity(rating))
					.toList();
		}).orElseThrow(()->new UserNotFoundByIdException("student is not found by the given id"));


	}

	private void sendVerificationOtpToUsers(String mail,int otp) {
		String text="<!DOCTYPE html>\n" +
				"<html lang=\"en\">\n" +
				"<head>\n" +
				"    <meta charset=\"UTF-8\">\n" +
				"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
				"    <title>Document</title>\n" +
				"</head>\n" +
				"<body>\n" +
				"    <h3>Please verify the email by providing otp below</h3>\n" +
				"    <p>This is from edu tracking system please verify your email by using below otp</p>\n" +
				"    <h4>"+otp+"</h4>\n" +
				"</body>\n" +
				"</html>";
		MessageModel messageModel=new MessageModel();
		messageModel.setTo(mail);
		messageModel.setSendDate(new Date());
		messageModel.setText(text);
		messageModel.setSubject("Verify your email");
		otpMailSender.sendMail(messageModel);

	}

	public UserResponse verifyOtp(OtpRequest otpRequest) {
		Integer otp=cacheHelper.getOtp(otpRequest.getEmail());
		if(!otp.equals(otpRequest.getOtp())){
			throw new InvalidOtpException("otp is incorrect or otp is expire, please try again");
		}
		User user=cacheHelper.getRegisterUser(otpRequest.getEmail());
		if(!user.getEmail().equals(otpRequest.getEmail()))
			throw new RegistrationSessionExpiredexception("Registartion Session Expired, Please try again");

		user = userRepository.save(user);
		return mapper.mapToUserResponse(user);
	}

	public String userLogin(LoginRequest loginRequest){
		UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword());
		Authentication authentication=authenticationManager.authenticate(authenticationToken);
		if(authentication.isAuthenticated()) {
			String token= userRepository.findByEmail(loginRequest.getEmail())
					.map(user -> {
						return jwtService.createJwt(user.getUserId(), user.getEmail(), user.getRole().name());

					}).orElseThrow(()->new UsernameNotFoundException("user name not found"));
			return token;
		}

		return null;

	}
}
