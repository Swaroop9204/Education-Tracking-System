package com.project.ets.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "super_admins")
public class SuperAdmin extends User{

}
