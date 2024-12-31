package com.example.job.utils;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.example.job.dto.JobDTO;

public interface ImageService {
    public ResponseEntity<Object> uploadImage(JobDTO job);
}
