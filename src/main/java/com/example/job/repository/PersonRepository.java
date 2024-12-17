package com.example.job.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.job.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

}
