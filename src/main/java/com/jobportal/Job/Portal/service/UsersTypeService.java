package com.jobportal.Job.Portal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.Job.Portal.entity.UsersType;
import com.jobportal.Job.Portal.repository.IUserTypeRepository;

@Service
public class UsersTypeService {

		private final IUserTypeRepository userTypeRepository;
		
		@Autowired
		public UsersTypeService(IUserTypeRepository userTypeRepository) {
			this.userTypeRepository = userTypeRepository;
		}
		
		public List<UsersType> getAll(){
			return userTypeRepository.findAll();
		}
}

