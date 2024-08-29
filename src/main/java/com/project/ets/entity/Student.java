package com.project.ets.entity;

import java.time.Year;

import com.project.ets.enums.Stack;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "students")
@Getter
@Setter
public class Student extends User{
	@Column(name = "degree")
	private String degree;
	@Column(name = "stream")
	private String stream;
	@Column(name = "year_of_passout")
	private Year yearOfPassout;
	@Column(name = "degree_percentage")
	private int degreePercentage;
	@Column(name = "twelth_percentage")
	private int twelthPercentage;
	@Column(name = "tenth percentage")
	private int tenthPercentage;
	@Column(name = "courses")
	@Enumerated(EnumType.STRING)
	private Stack stack;

}
