package com.example.job.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.job.repository.ApplyJobRepository;
import com.example.job.utils.CustomResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api")
public class ApplyJobRestController {
    private ApplyJobRepository applyJobRepository;

    @Autowired
    public ApplyJobRestController(ApplyJobRepository applyJobRepository) {
        this.applyJobRepository = applyJobRepository;
    }

    // @GetMapping("/applicant")
    // public ResponseEntity<Object> getApply(@RequestParam(required = false) String
    // search) {
    // return CustomResponse.generate(HttpStatus.OK, "Success get data",
    // applyJobRepository.searchApplicant(search));
    // }

    @GetMapping("/applicant")
    public Map<String, Object> getApplicant(@RequestParam("draw") int draw, @RequestParam("start") int start,
            @RequestParam("length") int length,
            @RequestParam(value = "search", defaultValue = "") String search) {
        int page = start / length;

        Pageable pageable = PageRequest.of(page, length);
        Page<Object[]> applicants = applyJobRepository.searchApplicant(search, pageable);

        long totalRecords = getTotalRecords();
        long filteredRecords = getFilterRecordsCount(search);

        Map<String, Object> response = new HashMap<>();
        response.put("draw", draw);
        response.put("recordsTotal", totalRecords);
        response.put("recordsFiltered", filteredRecords);
        response.put("data", applicants.getContent());

        return response;
    }

    private long getTotalRecords() {
        return applyJobRepository.count();
    }

    private long getFilterRecordsCount(String search) {
        return applyJobRepository.countFiltered(search);
    }

}
