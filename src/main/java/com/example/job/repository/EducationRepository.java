package com.example.job.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.job.model.Education;

@Repository
public interface EducationRepository extends JpaRepository<Education, Integer> {

}
