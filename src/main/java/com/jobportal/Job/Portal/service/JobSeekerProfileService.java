package com.jobportal.Job.Portal.service;

import java.util.Optional;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jobportal.Job.Portal.entity.JobSeekerProfile;

import com.jobportal.Job.Portal.entity.Users;
import com.jobportal.Job.Portal.repository.IJobSeekerProfileRepository;
import com.jobportal.Job.Portal.repository.IUserRepository;

@Service
public class JobSeekerProfileService {
	
	private final IJobSeekerProfileRepository jobSeekerProfileRepository;
	private final IUserRepository userRepository;
	
	public JobSeekerProfileService(IJobSeekerProfileRepository jobSeekerProfileRepository,IUserRepository userRepository) {
		this.jobSeekerProfileRepository = jobSeekerProfileRepository;
		this.userRepository = userRepository;
	}

	public Optional<JobSeekerProfile> getOne(Integer id){
		return jobSeekerProfileRepository.findById(id);
	}

	public JobSeekerProfile addNew(JobSeekerProfile jobSeekerProfile) {
		
		return jobSeekerProfileRepository.save(jobSeekerProfile);
	}

	public JobSeekerProfile getCurrentSeekerProfile() {
		{

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if(!(authentication instanceof AnonymousAuthenticationToken)) {
				String currentUsername = authentication.getName();
				Users users = userRepository.findByEmail(currentUsername).orElseThrow(()-> new UsernameNotFoundException("User not found"));
				Optional<JobSeekerProfile> jobSeekerProfile =getOne(users.getUserId());
				return jobSeekerProfile.orElse(null);
			}else return null;
		}
		
	}
}
