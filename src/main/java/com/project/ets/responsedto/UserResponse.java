package com.project.ets.responsedto;

import java.time.LocalDateTime;

import com.project.ets.enums.UserRole;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
	
	
	private String username;
	private String email;
	private UserRole role;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	
}
