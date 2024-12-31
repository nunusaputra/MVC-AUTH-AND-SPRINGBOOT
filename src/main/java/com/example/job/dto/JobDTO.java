package com.example.job.dto;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobDTO {
    private Integer id;
    private String jobTitle;
    private Integer sallary;
    private String description;
    private Boolean isActive;
    private Integer personId;
    private String fullname;
    private Integer jobTypeId;
    private String jobTypeName;
    private String path;

    @JsonIgnore
    private MultipartFile file;

    public JobDTO(
            Integer id,
            String jobTitle,
            Integer sallary,
            String description,
            Boolean isActive,
            Integer personId,
            String fullname,
            Integer jobTypeId,
            String jobTypeName,
            String path) {
        this.id = id;
        this.jobTitle = jobTitle;
        this.sallary = sallary;
        this.description = description;
        this.isActive = isActive;
        this.personId = personId;
        this.fullname = fullname;
        this.jobTypeId = jobTypeId;
        this.jobTypeName = jobTypeName;
        this.path = path;
    }
}
