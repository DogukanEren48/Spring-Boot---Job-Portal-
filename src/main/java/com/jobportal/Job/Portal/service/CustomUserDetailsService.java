package com.jobportal.Job.Portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.jobportal.Job.Portal.entity.Users;
import com.jobportal.Job.Portal.repository.IUserRepository;
import com.jobportal.Job.Portal.util.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	
	private final IUserRepository userRepository;
	
	@Autowired
	public CustomUserDetailsService(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users users = userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("Could not found user"));
		return new CustomUserDetails(users);
	}

	
}
