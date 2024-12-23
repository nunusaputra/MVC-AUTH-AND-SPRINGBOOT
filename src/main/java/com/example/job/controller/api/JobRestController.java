package com.example.job.controller.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.job.dto.JobDTO;
import com.example.job.dto.JobStatusDTO;
import com.example.job.model.Job;
import com.example.job.model.JobType;
import com.example.job.model.Person;
import com.example.job.repository.JobRepository;
import com.example.job.repository.JobTypeRepository;
import com.example.job.repository.PersonRepository;
import com.example.job.utils.CustomResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api")
public class JobRestController {
    private JobRepository jobRepository;
    private JobTypeRepository jobTypeRepository;
    private PersonRepository personRepository;

    @Autowired
    public JobRestController(JobRepository jobRepository, JobTypeRepository jobTypeRepository,
            PersonRepository personRepository) {
        this.jobRepository = jobRepository;
        this.jobTypeRepository = jobTypeRepository;
        this.personRepository = personRepository;
    }

    @GetMapping("/job")
    public ResponseEntity<Object> getJob() {

        return CustomResponse.generate(HttpStatus.OK, "Success get data job", jobRepository.findAll());
    }

    @GetMapping("/job/{id}")
    public ResponseEntity<Object> getJobId(@PathVariable Integer id) {
        Job job = jobRepository.findById(id).orElse(null);

        if (job == null) {
            return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Job not found!");
        } else {
            return CustomResponse.generate(HttpStatus.OK, "Success get job by id", job);
        }
    }

    @PostMapping("/job")
    public ResponseEntity<Object> postJob(@RequestBody JobDTO job) {
        try {
            JobType jobtype = jobTypeRepository.findById(job.getJobTypeId()).orElse(null);

            if (jobtype == null) {
                return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Job Type tidak tersedia");
            }

            Person person = personRepository.findById(job.getPersonId()).orElse(null);

            if (person == null) {
                return CustomResponse.generate(HttpStatus.NOT_FOUND, "Person not found");
            }

            Job jobs = new Job();
            jobs.setJobTitle(job.getJobTitle());
            jobs.setJobtype(jobtype);
            jobs.setSallary(job.getSalary());
            jobs.setDescription(job.getDescription());
            jobs.setPerson(person);
            return CustomResponse.generate(HttpStatus.CREATED, "Data job berhasil dibuat", jobRepository.save(jobs));
        } catch (Exception e) {
            return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Job gagal dibuat!", e.getMessage());
        }
    }

    @PutMapping("/job/{id}")
    public ResponseEntity<Object> putJob(@PathVariable Integer id, @RequestBody JobDTO jobPut) {
        try {
            Job job = jobRepository.findById(id).orElse(null);
            if (job == null) {
                return CustomResponse.generate(HttpStatus.NOT_FOUND, "Job not found!");
            }

            job.setJobTitle(jobPut.getJobTitle());
            job.setSallary(jobPut.getSalary());
            job.setDescription(jobPut.getDescription());
            job.setIsActive(jobPut.getIsActive());

            jobRepository.save(job);

            return CustomResponse.generate(HttpStatus.OK, "Job successfully updated");
        } catch (Exception e) {
            return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Job failed to update!", e.getMessage());
        }
    }

    @PatchMapping("/job/{id}")
    public ResponseEntity<Object> patchJob(@PathVariable Integer id, @RequestBody JobStatusDTO status) {
        try {
            Job statusJob = jobRepository.findById(id).orElse(null);

            if (statusJob == null) {
                return CustomResponse.generate(HttpStatus.NOT_FOUND, "Job not found");
            }

            statusJob.setIsActive(status.getIsActive());
            return CustomResponse.generate(HttpStatus.OK, "Successfully update status job",
                    jobRepository.save(statusJob));
        } catch (Exception e) {
            return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Failed update status job", e.getMessage());
        }
    }

    @DeleteMapping("/job/{id}")
    public ResponseEntity<Object> deleteJob(@PathVariable Integer id) {
        try {
            Boolean existsJob = jobRepository.existsById(id);

            if (!existsJob) {
                return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Job not exists!");
            }

            jobRepository.deleteById(id);
            return CustomResponse.generate(HttpStatus.OK, "Successfully delete a job");
        } catch (Exception e) {
            return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Failed delete a job", e.getMessage());
        }
    }

}
