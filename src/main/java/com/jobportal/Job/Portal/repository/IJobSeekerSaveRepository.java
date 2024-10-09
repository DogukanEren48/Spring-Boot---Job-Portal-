package com.jobportal.Job.Portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jobportal.Job.Portal.entity.JobPostActivity;
import com.jobportal.Job.Portal.entity.JobSeekerProfile;
import com.jobportal.Job.Portal.entity.JobSeekerSave;

@Repository
public interface IJobSeekerSaveRepository extends JpaRepository<JobSeekerSave, Integer>{

	
	 List<JobSeekerSave> findByUserId(JobSeekerProfile userAccountId);
	 
	 List<JobSeekerSave> findByJob(JobPostActivity job);

	
}
