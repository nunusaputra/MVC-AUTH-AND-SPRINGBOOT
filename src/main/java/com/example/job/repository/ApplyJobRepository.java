package com.example.job.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.job.model.ApplyJob;

@Repository
public interface ApplyJobRepository extends JpaRepository<ApplyJob, Integer> {

}
