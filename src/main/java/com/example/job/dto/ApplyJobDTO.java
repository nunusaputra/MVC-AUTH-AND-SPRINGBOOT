package com.example.job.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyJobDTO {
    private String fullname;
    private Double gpa;
    private String university;
    private String faculty;
    private String companyName;
    private String jobTitle;
    private Integer sallary;
    private String jobType;
    private String status;
}
