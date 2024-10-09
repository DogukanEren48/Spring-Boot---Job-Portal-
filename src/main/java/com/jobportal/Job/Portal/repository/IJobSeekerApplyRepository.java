package com.jobportal.Job.Portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jobportal.Job.Portal.entity.JobPostActivity;
import com.jobportal.Job.Portal.entity.JobSeekerApply;
import com.jobportal.Job.Portal.entity.JobSeekerProfile;


@Repository
public interface IJobSeekerApplyRepository extends JpaRepository<JobSeekerApply, Integer>{

	
	List<JobSeekerApply> findByUserId(JobSeekerProfile userId);
	
	List<JobSeekerApply> findByJob(JobPostActivity job);
	 
}
