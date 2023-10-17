package com.smart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
//	This code is for login functionality
//	@param("eamil") is use for pass email to query
	
	
	@Query("select u from User u where u.email=:email")
	public User getUserByUserName(@Param("email") String email);
	

}
