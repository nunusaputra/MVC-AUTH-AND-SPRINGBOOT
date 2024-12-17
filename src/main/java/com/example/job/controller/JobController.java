package com.example.job.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.job.model.Job;
import com.example.job.model.JobType;
import com.example.job.repository.JobRepository;
import com.example.job.repository.JobTypeRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("job")
public class JobController {
    private JobRepository jobRepository;
    private JobTypeRepository jobTypeRepository;

    @Autowired
    public JobController(JobRepository jobRepository, JobTypeRepository jobTypeRepository) {
        this.jobRepository = jobRepository;
        this.jobTypeRepository = jobTypeRepository;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("jobs", jobRepository.getJob());
        return "job/index";
    }

    @GetMapping(value = { "form", "form/{id}" })
    public String form(@PathVariable(required = false) Integer id, Model model) {
        Job job = new Job();

        if (id != null) {
            job = jobRepository.findById(id).orElse(null);
            model.addAttribute("job", job);
        } else {
            model.addAttribute("job", new Job());
        }

        model.addAttribute("jobType", jobTypeRepository.findAll());
        return "job/form";
    }

    @PostMapping("save")
    public String save(Job job) {
        JobType jobtype = jobTypeRepository.findById(job.getJobtype().getId()).orElse(null);
        job.setJobtype(jobtype);

        jobRepository.save(job);

        return "redirect:/job";
    }

    @PostMapping("delete/{id}")
    public String delete(@PathVariable Integer id) {
        jobRepository.deleteById(id);
        return "redirect:/job";
    }

}
