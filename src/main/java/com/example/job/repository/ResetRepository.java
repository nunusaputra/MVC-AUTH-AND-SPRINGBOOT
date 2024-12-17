package com.example.job.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.job.model.ResetPassword;

@Repository
public interface ResetRepository extends JpaRepository<ResetPassword, Integer> {
    ResetPassword findByToken(String token);
}
