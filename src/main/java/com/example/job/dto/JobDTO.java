package com.example.job.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobDTO {
    private Integer id;
    private String jobTitle;
    private Integer salary;
    private String description;
    private Boolean isActive;
    private Integer personId;
    private Integer jobTypeId;
}
