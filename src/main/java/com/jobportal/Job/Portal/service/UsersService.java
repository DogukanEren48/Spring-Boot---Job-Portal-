package com.jobportal.Job.Portal.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jobportal.Job.Portal.entity.JobSeekerProfile;
import com.jobportal.Job.Portal.entity.RecruiterProfile;
import com.jobportal.Job.Portal.entity.Users;
import com.jobportal.Job.Portal.repository.IJobSeekerProfileRepository;
import com.jobportal.Job.Portal.repository.IRecruiterProfileRepository;
import com.jobportal.Job.Portal.repository.IUserRepository;

@Service
public class UsersService {
	
	private final IUserRepository userRepository;
	private final IJobSeekerProfileRepository jobSeekerProfileRepository;
	private final IRecruiterProfileRepository recruiterProfileRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public UsersService(IUserRepository userRepository,IJobSeekerProfileRepository jobSeekerProfileRepository,
			IRecruiterProfileRepository recruiterProfileRepository,PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.jobSeekerProfileRepository=jobSeekerProfileRepository;
		this.recruiterProfileRepository=recruiterProfileRepository;
		this.passwordEncoder=passwordEncoder;
	}

	public Users addNew(Users users) {
		users.setActive(true);
		users.setRegistrationDate(new Date(System.currentTimeMillis()));
		users.setPassword(passwordEncoder.encode(users.getPassword()));
		Users savedUser = userRepository.save(users);
		int userTypeId = users.getUserTypeId().getUserTypeId();
		if(userTypeId == 1)
		{
			recruiterProfileRepository.save(new RecruiterProfile(savedUser));
		}else {
			jobSeekerProfileRepository.save(new JobSeekerProfile(savedUser));
		}
		
		return savedUser;
	}
	
	public Optional<Users> getUserByEmail(String email){
		return userRepository.findByEmail(email);
	}

	public Object getCurrentUserProfile() {
		// TODO Auto-generated method stub
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(!(authentication instanceof AnonymousAuthenticationToken)) {
			String username = authentication.getName();
			Users users = userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("Could not found" + "user"));
			
			int userId = users.getUserId();
			if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
				RecruiterProfile recruiterProfile = recruiterProfileRepository.findById(userId).orElse(new RecruiterProfile());
				return recruiterProfile;
			}else {
				JobSeekerProfile jobSeekerProfile = jobSeekerProfileRepository.findById(userId).orElse(new JobSeekerProfile());
				return jobSeekerProfile;
			}
			
			
		}
		return null;
	}

	public Users getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(!(authentication instanceof AnonymousAuthenticationToken)) {
			String username = authentication.getName();
			Users user = userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("Could not found" + "user"));
			
			return user;
		}
		return null;
	}

	public Users findByEmail(String currentUsername) {
		return userRepository.findByEmail(currentUsername).orElseThrow(()-> new UsernameNotFoundException("User not found"));
	}
	
}
