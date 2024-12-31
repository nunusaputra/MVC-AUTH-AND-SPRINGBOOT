package com.example.job.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.job.dto.ApplyJobDTO;
import com.example.job.model.ApplyJob;

@Repository
public interface ApplyJobRepository extends JpaRepository<ApplyJob, Integer> {
    @Query(value = "SELECT DISTINCT p.fullname as applicant_name, e.gpa, u.name as university, "
            + "f.name as faculty, cp.fullname as company_name, j.job_title, j.sallary, jt.name as job_type, aj.status "
            + "FROM tb_person p " + "LEFT JOIN tb_biodata b ON p.id = b.person_id "
            + "LEFT JOIN tb_education e ON e.id = b.edu_id " + "LEFT JOIN tb_university u ON u.id = e.univ_id "
            + "LEFT JOIN tb_faculty f ON f.id = e.faculty_id " + "LEFT JOIN tb_apply_job aj ON p.id = aj.person_id "
            + "LEFT JOIN tb_job j ON j.id = aj.job_id " + "LEFT JOIN tb_job_type jt ON jt.id = j.job_type_id "
            + "LEFT JOIN tb_person cp ON j.person_id = cp.id "
            + "WHERE p.id != 1 AND p.fullname LIKE CONCAT('%',:search,'%') OR e.gpa LIKE CONCAT('%',:search,'%') "
            + "OR u.name LIKE CONCAT('%',:search,'%') OR f.name LIKE CONCAT('%',:search,'%') OR j.job_title LIKE CONCAT('%',:search,'%') "
            + "OR j.sallary LIKE CONCAT('%',:search,'%') OR jt.name LIKE CONCAT('%',:search,'%') OR aj.status LIKE CONCAT('%',:search,'%')", nativeQuery = true)
    Page<Object[]> searchApplicant(@Param(value = "search") String search, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM tb_apply_job j ", nativeQuery = true)
    long countFiltered(String search);
}
