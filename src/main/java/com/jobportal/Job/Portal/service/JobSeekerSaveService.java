package com.jobportal.Job.Portal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jobportal.Job.Portal.entity.JobPostActivity;
import com.jobportal.Job.Portal.entity.JobSeekerProfile;
import com.jobportal.Job.Portal.entity.JobSeekerSave;
import com.jobportal.Job.Portal.repository.IJobSeekerSaveRepository;

@Service
public class JobSeekerSaveService {
	
	private final IJobSeekerSaveRepository jobSeekerSaveRepository;

	public JobSeekerSaveService(IJobSeekerSaveRepository jobSeekerSaveRepository) {
		this.jobSeekerSaveRepository = jobSeekerSaveRepository;
	}
	
	public List<JobSeekerSave> getCandidatesJob(JobSeekerProfile userAccountId){
		return jobSeekerSaveRepository.findByUserId(userAccountId);
	}
	
	public List<JobSeekerSave> getJobCandidates(JobPostActivity job){
		return jobSeekerSaveRepository.findByJob(job);
	}

	public void addNew(JobSeekerSave jobSeekerSave) {
		jobSeekerSaveRepository.save(jobSeekerSave);
		
	}


	

}
