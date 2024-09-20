package com.project.ets.service;

import java.util.*;

import com.project.ets.exception.InvalidOtpException;
import com.project.ets.exception.RegistrationSessionExpiredexception;
import com.project.ets.requstdto.LoginRequest;
import com.project.ets.requstdto.OtpRequest;
import com.project.ets.security.JWT_Service;
import com.project.ets.util.CacheHelper;
import com.project.ets.util.MailSenderService;
import com.project.ets.util.MessageModel;
import com.project.ets.util.ResponseStructure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;
    private final MailSenderService otpMailSender;
    private final Random random;
    private final CacheHelper cacheHelper;
    private final AuthenticationManager authenticationManager;
    private final JWT_Service jwtService;
    @Value("${my_app.jwt.access_expiry}")
    private long access_expiry;

    @Value("${my_app.jwt.refresh_expiry}")
    private long refresh_expiry;

    public UserService(UserRepository userRepository,
                       UserMapper mapper,
                       RatingRepository ratingRepository,
                       RatingMapper ratingMapper,
                       MailSenderService otpMailSender,
                       Random random,
                       CacheHelper cacheHelper,
                       AuthenticationManager authenticationManager,
                       JWT_Service jwtService) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.ratingRepository = ratingRepository;
        this.ratingMapper = ratingMapper;
        this.otpMailSender = otpMailSender;
        this.random = random;
        this.cacheHelper = cacheHelper;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public UserResponse saveUser(RegistrationRequest registrationRequest, UserRole role) {
        User user = null;
        switch (role) {
            case ADMIN -> user = new Admin();
            case HR -> user = new HR();
            case STUDENT -> user = new Student();
            case TRAINER -> user = new Trainer();
            default -> throw new IllegalArgumentException("Unexpected value: " + role);
        }
        if (user != null) {
            user = mapper.mapToUserEntity(registrationRequest, user);
            user.setRole(role);
            int otp = random.nextInt(100000, 999999);
            cacheHelper.cacheUser(user);
            cacheHelper.cacheOtp(otp, user.getEmail());
            sendVerificationOtpToUsers(user.getEmail(), otp);
        }
        return mapper.mapToUserResponse(user);
    }

    public UserResponse updateTrainer(TrainerRequest trainerRequest, String userId) {
        return userRepository.findById(userId).map((user) -> {
            user = mapper.mapToTrainerEntity(trainerRequest, (Trainer) user);
            user = userRepository.save(user);
            return mapper.mapToUserResponse(user);
        }).orElseThrow(() -> new UserNotFoundByIdException("failed to update the trainer"));
    }

    public StudentResponse updateStudent(StudentRequest studentRequest, String userId) {
        return userRepository.findById(userId).map((user) -> {
            user = mapper.mapToStudentEntity(studentRequest, (Student) user);
            user = userRepository.save(user);
            return mapper.mapToStudentResponse((Student) user);
        }).orElseThrow(() -> new UserNotFoundByIdException("failed to update the student"));
    }

    public StudentResponse updateStudent(Stack stack, String userId) {
        return userRepository.findById(userId).map(user -> {
            Student student = (Student) user;
            stack.getSubjects().forEach(subject -> {
                Rating rating = new Rating();
                rating.setSubject(subject);
                rating.setStudent(student);
                rating = ratingRepository.save(rating);
            });
            student.setStack(stack);
            user = userRepository.save(student);
            return mapper.mapToStudentResponse(student);
        }).orElseThrow(() -> new UserNotFoundByIdException("faied to update stack to the student"));
    }

    public List<RatingResponse> viewRating(String userId) {
        return userRepository.findById(userId).map(user -> {
            Student student = (Student) user;
            return student.getRatings()
                    .stream()
                    .map(ratingMapper::mapToRatingResponseEntity)
                    .toList();
        }).orElseThrow(() -> new UserNotFoundByIdException("student is not found by the given id"));


    }

    private void sendVerificationOtpToUsers(String mail, int otp) {
        String text = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Document</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h3>Please verify the email by providing otp below</h3>\n" +
                "    <p>This is from edu tracking system please verify your email by using below otp</p>\n" +
                "    <h4>" + otp + "</h4>\n" +
                "</body>\n" +
                "</html>";
        MessageModel messageModel = new MessageModel();
        messageModel.setTo(mail);
        messageModel.setSendDate(new Date());
        messageModel.setText(text);
        messageModel.setSubject("Verify your email");
        otpMailSender.sendMail(messageModel);

    }

    public UserResponse verifyOtp(OtpRequest otpRequest) {
        Integer otp = cacheHelper.getOtp(otpRequest.getEmail());
        if (!otp.equals(otpRequest.getOtp())) {
            throw new InvalidOtpException("otp is incorrect or otp is expire, please try again");
        }
        User user = cacheHelper.getRegisterUser(otpRequest.getEmail());
        if (!user.getEmail().equals(otpRequest.getEmail()))
            throw new RegistrationSessionExpiredexception("Registartion Session Expired, Please try again");

        user = userRepository.save(user);
        return mapper.mapToUserResponse(user);
    }

    public ResponseEntity<ResponseStructure<UserResponse>> userLogin(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        if (authentication.isAuthenticated()) {
            return userRepository.findByEmail(loginRequest.getEmail())
                    .map(user -> {
                        HttpHeaders httpHeaders = new HttpHeaders();
                        grantAccessAccessToken(user, httpHeaders);
                        grantAccessRefreshToken(user, httpHeaders);
                        return ResponseEntity.ok().headers(httpHeaders).body(ResponseStructure.create(HttpStatus.OK.value(), "login successfulyy", mapper.mapToUserResponse(user)));
                    }).orElseThrow(() -> new UsernameNotFoundException("user name not found"));
        } else {
            throw new UsernameNotFoundException("login failed");
        }
    }

    private void grantAccessAccessToken(User user, HttpHeaders httpHeaders) {
        String access_token = jwtService.generateAccessToken(user.getUserId(), user.getEmail(), user.getRole().name());
        httpHeaders.add(HttpHeaders.SET_COOKIE, createCookie("at", access_token, access_expiry * 60));
        return;
    }

    private void grantAccessRefreshToken(User user, HttpHeaders httpHeaders) {
        String refresh_token = jwtService.generateRefreshToken(user.getUserId(), user.getEmail(), user.getRole().name());
        httpHeaders.add(HttpHeaders.SET_COOKIE, createCookie("rt", refresh_token, refresh_expiry * 60));
        return ;
    }

    private String createCookie(String name, String value, long age) {
        return ResponseCookie.from(name, value)
                .domain("localhost")
                .path("/")
                .secure(false)
                .httpOnly(true)
                .sameSite("Lax")
                .maxAge(age)
                .build()
                .toString();
    }

    public ResponseEntity<ResponseStructure<UserResponse>> refreshLogin() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).map(user -> {
            HttpHeaders httpHeaders = new HttpHeaders();
            grantAccessAccessToken(user, httpHeaders);
            return ResponseEntity.ok().headers(httpHeaders).body(ResponseStructure.create(HttpStatus.OK.value(), "refresh login successfulyy", mapper.mapToUserResponse(user)));
        }).orElseThrow(() -> new UsernameNotFoundException("username is not found"));
    }
}
