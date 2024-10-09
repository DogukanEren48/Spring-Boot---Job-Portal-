package com.jobportal.Job.Portal.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.Job.Portal.entity.IRecruiterJobs;
import com.jobportal.Job.Portal.entity.JobCompany;
import com.jobportal.Job.Portal.entity.JobLocation;
import com.jobportal.Job.Portal.entity.JobPostActivity;
import com.jobportal.Job.Portal.entity.RecruiterJobsDto;
import com.jobportal.Job.Portal.repository.IJobPostActivityRepository;

@Service
public class JobPostActivityService {
	
	private final IJobPostActivityRepository jobPostActivityRepository;
	
	
	@Autowired
	public JobPostActivityService(IJobPostActivityRepository jobPostActivityRepository) {
		this.jobPostActivityRepository = jobPostActivityRepository;
	}

	public JobPostActivity addNew(JobPostActivity jobPostActivity) {
		return jobPostActivityRepository.save(jobPostActivity);
	}

	public List<RecruiterJobsDto> getRecruiterJobs(int recruiter){
		
		List<IRecruiterJobs> recruiterJobsDtos = jobPostActivityRepository.getRecruiterJobs(recruiter);
		
		List<RecruiterJobsDto> recruiterJobsDtosList = new ArrayList<>();
		
		for(IRecruiterJobs rec : recruiterJobsDtos) {
			JobLocation loc = new JobLocation(rec.getLocationId(),rec.getCity(),rec.getState(),rec.getCountry());
			JobCompany comp = new JobCompany(rec.getCompanyId(),rec.getName(), "");
			recruiterJobsDtosList.add(new RecruiterJobsDto(rec.getTotalCandidates(), rec.getJob_post_id(), rec.getJob_title(), loc, comp));
			
		}
		return recruiterJobsDtosList;
	}

	public JobPostActivity getOne(int id) {
		
		return jobPostActivityRepository.findById(id).orElseThrow(()->new RuntimeException("Jobnot found"));
	}

	public List<JobPostActivity> getAll() {
		return jobPostActivityRepository.findAll();
		
	}

	public List<JobPostActivity> search(String job, String location, List<String> type, List<String> remote,
			LocalDate searchDate) {
		return Objects.isNull(searchDate)?jobPostActivityRepository.searchWithoutDate(job,location,remote,type):
			jobPostActivityRepository.search(job, location , remote, type, searchDate);
	}

}
