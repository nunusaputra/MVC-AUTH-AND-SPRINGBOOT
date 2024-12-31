package com.example.job.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.job.model.Assets;

@Repository
public interface AssetsRepository extends JpaRepository<Assets, Integer> {

}
