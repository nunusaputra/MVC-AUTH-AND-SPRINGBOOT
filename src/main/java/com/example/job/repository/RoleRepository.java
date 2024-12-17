package com.example.job.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.job.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query("SELECT u FROM Role u WHERE u.level = :level")
    Role findByLevel(@Param(value = "level") Integer level);
}
