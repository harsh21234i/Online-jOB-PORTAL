package com.jobportal.job_portal.repository;

import com.jobportal.job_portal.Entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    // 🔎 Search by keyword in title or skills (paginated)
    @Query("SELECT j FROM Job j " +
            "WHERE (:keyword IS NULL OR LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(j.skills) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Job> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    // 🔎 Filter by location & jobType
    List<Job> findByLocationIgnoreCaseAndJobTypeIgnoreCase(String location, String jobType);

    // 🔎 Salary range filter
    List<Job> findBySalaryBetween(Double minSalary, Double maxSalary);

    // ✅ Flexible search with multiple filters + pagination
    @Query("SELECT j FROM Job j " +
            "WHERE (:title IS NULL OR LOWER(j.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND (:company IS NULL OR LOWER(j.companyName) LIKE LOWER(CONCAT('%', :company, '%'))) " +
            "AND (:location IS NULL OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%'))) " +
            "AND (:minSalary IS NULL OR j.salary >= :minSalary) " +
            "AND (:maxSalary IS NULL OR j.salary <= :maxSalary)")
    Page<Job> searchJobs(@Param("title") String title,
                         @Param("company") String company,
                         @Param("location") String location,
                         @Param("minSalary") Double minSalary,
                         @Param("maxSalary") Double maxSalary,
                         Pageable pageable);
}
