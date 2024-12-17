package com.example.job.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.job.model.Experience;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Integer> {

}
