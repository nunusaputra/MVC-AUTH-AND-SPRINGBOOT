package com.example.job.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.job.model.Biodata;

@Repository
public interface BiodataRepository extends JpaRepository<Biodata, Integer> {

}
