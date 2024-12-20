package com.example.job.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.job.dto.JobDTO;
import com.example.job.model.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    // @Query("SELECT new com.example.job.dto.JobDTO(u.id, u.jobTitle, u.sallary,
    // u.description, u.jobtype.name, u.person.id)"
    // +
    // "FROM Job u JOIN u.jobtype u JOIN u.person u")
    // List<JobDTO> getJob();
}
