package com.project.ets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.ets.entity.User;
import com.project.ets.enums.UserRole;
@Repository
public interface UserRepository extends JpaRepository<User, String>{
	@Query("from User where role=:role")
	List<User> findByRole(UserRole role);

}
