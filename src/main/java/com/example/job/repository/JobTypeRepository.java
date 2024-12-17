package com.example.job.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.job.model.JobType;

@Repository
public interface JobTypeRepository extends JpaRepository<JobType, Integer> {

}
