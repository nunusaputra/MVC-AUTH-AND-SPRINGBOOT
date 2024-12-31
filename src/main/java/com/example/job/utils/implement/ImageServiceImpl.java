package com.example.job.utils.implement;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.job.dto.JobDTO;
import com.example.job.model.Assets;
import com.example.job.model.Job;
import com.example.job.model.JobType;
import com.example.job.model.Person;
import com.example.job.repository.AssetsRepository;
import com.example.job.repository.JobRepository;
import com.example.job.repository.JobTypeRepository;
import com.example.job.repository.PersonRepository;
import com.example.job.utils.CloudinaryService;
import com.example.job.utils.CustomResponse;
import com.example.job.utils.ImageService;

@Service
public class ImageServiceImpl implements ImageService {
    private CloudinaryService cloudinaryService;
    private JobRepository jobRepository;
    private JobTypeRepository jobTypeRepository;
    private PersonRepository personRepository;
    private AssetsRepository assetsRepository;

    @Autowired
    public ImageServiceImpl(CloudinaryService cloudinaryService, JobRepository jobRepository,
            JobTypeRepository jobTypeRepository, PersonRepository personRepository, AssetsRepository assetsRepository) {
        this.cloudinaryService = cloudinaryService;
        this.jobRepository = jobRepository;
        this.jobTypeRepository = jobTypeRepository;
        this.personRepository = personRepository;
        this.assetsRepository = assetsRepository;
    }

    @Override
    public ResponseEntity<Object> uploadImage(JobDTO job) {
        try {
            JobType jobtype = jobTypeRepository.findById(job.getJobTypeId()).orElse(null);

            if (jobtype == null) {
                return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Job Type tidak tersedia");
            }

            Person person = personRepository.findById(job.getPersonId()).orElse(null);

            if (person == null) {
                return CustomResponse.generate(HttpStatus.NOT_FOUND, "Person not found");
            }

            if (job.getFile().isEmpty()) {
                return CustomResponse.generate(HttpStatus.BAD_REQUEST, "File cannot be null");
            }

            String imgUrl = cloudinaryService.uploadFile(job.getFile());

            Job jobs = new Job();
            jobs.setJobTitle(job.getJobTitle());
            jobs.setJobtype(jobtype);
            jobs.setSallary(job.getSallary());
            jobs.setDescription(job.getDescription());
            jobs.setIsActive(job.getIsActive());
            jobs.setPerson(person);

            jobs = jobRepository.save(jobs);

            Assets assets = new Assets();
            assets.setPath(imgUrl);
            assets.setJob(jobs);
            jobs.setAssets(assets);

            return CustomResponse.generate(HttpStatus.CREATED, "Data job berhasil dibuat", jobRepository.save(jobs));
        } catch (Exception e) {
            return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Failed upload a job", e.getMessage());
        }
    }

}
