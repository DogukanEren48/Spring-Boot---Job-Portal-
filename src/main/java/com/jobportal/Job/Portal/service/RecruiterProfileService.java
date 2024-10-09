package com.jobportal.Job.Portal.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jobportal.Job.Portal.entity.RecruiterProfile;
import com.jobportal.Job.Portal.entity.Users;
import com.jobportal.Job.Portal.repository.IRecruiterProfileRepository;
import com.jobportal.Job.Portal.repository.IUserRepository;

@Service
public class RecruiterProfileService {
	
	private final IRecruiterProfileRepository recruiterProfileRepository;
	private final IUserRepository userRepository;
	
	@Autowired
	public RecruiterProfileService(IRecruiterProfileRepository recruiterProfileRepository,IUserRepository userRepository) {
		
		this.recruiterProfileRepository = recruiterProfileRepository;
		this.userRepository = userRepository;
	}
	
	public Optional<RecruiterProfile> getOne(Integer id){
		return recruiterProfileRepository.findById(id);
	}

	public RecruiterProfile addNew(RecruiterProfile recruiterProfile) {
		
		return recruiterProfileRepository.save(recruiterProfile);
	}

	public RecruiterProfile getCurrentRecruiterProfile() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUsername = authentication.getName();
			Users users = userRepository.findByEmail(currentUsername).orElseThrow(()-> new UsernameNotFoundException("User not found"));
			Optional<RecruiterProfile> recruiterProfile =getOne(users.getUserId());
			return recruiterProfile.orElse(null);
		}else return null;
	}
	
}
