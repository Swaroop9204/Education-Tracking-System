package com.project.ets.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.project.ets.config.GenerateSequenceId;
import com.project.ets.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@EntityListeners(AuditingEntityListener.class)
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
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private UserRole role;
	
	@CreatedDate
	@Column(name = "createdDate")
	private LocalDateTime createdDate;
	
	@LastModifiedDate
	@Column(name = "modifiedDate")
	private LocalDateTime modifiedDate;

}
