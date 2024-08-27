package com.project.ets.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.project.ets.config.GenerateSequenceId;
import com.project.ets.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
	@Id
	@Column(name = "userId")
	@GenerateSequenceId
	private String userId;
	
	@Column(name = "user_name")
	private String username;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private UserRole role;
	
	@Column(name = "createdDate")
	private LocalDateTime createdDate;
	
	@Column(name = "modifiedDate")
	private LocalDateTime modifiedDate;

}
