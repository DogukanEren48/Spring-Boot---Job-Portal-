package com.jobportal.Job.Portal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jobportal.Job.Portal.entity.JobPostActivity;
import com.jobportal.Job.Portal.entity.JobSeekerApply;
import com.jobportal.Job.Portal.entity.JobSeekerProfile;
import com.jobportal.Job.Portal.repository.IJobSeekerApplyRepository;

@Service
public class JobSeekerApplyService {
	
	private final IJobSeekerApplyRepository jobSeekerApplyRepository;
	
	public JobSeekerApplyService(IJobSeekerApplyRepository jobSeekerApplyRepository) {
		this.jobSeekerApplyRepository = jobSeekerApplyRepository;
	}
	
	public List<JobSeekerApply> getCandidatesJobs(JobSeekerProfile userAccountId){
		return jobSeekerApplyRepository.findByUserId(userAccountId);
	}
	
	public List<JobSeekerApply> getJobCandidates(JobPostActivity job){
		return jobSeekerApplyRepository.findByJob(job);
	}

	public void addNew(JobSeekerApply jobSeekerApply) {
		jobSeekerApplyRepository.save(jobSeekerApply);
		
	}
}
