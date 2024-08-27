package com.project.ets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.ets.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, String>{

}
