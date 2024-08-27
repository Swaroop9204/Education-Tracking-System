package com.project.ets.entity;

import java.util.List;

import com.project.ets.enums.Subject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
@Table(name = "trainers")
public class Trainer extends User{
	@Column(name = "subjects")
	private List<Subject> subjects;

}
