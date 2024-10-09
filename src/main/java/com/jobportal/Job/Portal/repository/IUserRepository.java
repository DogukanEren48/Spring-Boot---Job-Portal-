package com.jobportal.Job.Portal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.Job.Portal.entity.Users;

public interface IUserRepository extends JpaRepository<Users, Integer>{
	
	Optional<Users> findByEmail(String email); 
	
}
