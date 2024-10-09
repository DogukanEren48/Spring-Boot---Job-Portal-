package com.jobportal.Job.Portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.Job.Portal.entity.UsersType;

public interface IUserTypeRepository extends JpaRepository<UsersType, Integer>{

}
