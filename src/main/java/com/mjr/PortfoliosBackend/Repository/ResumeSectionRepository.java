package com.mjr.PortfoliosBackend.Repository;

import com.mjr.PortfoliosBackend.Model.ResumeSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeSectionRepository extends JpaRepository<ResumeSection, Integer> {

    public ResumeSection getById(int id);

    // GET RESUME SECTIONS OF RESUME
    @Query(
            value = "SELECT * FROM resume_sections WHERE resume_id = ?1",
            nativeQuery = true
    )
    public List<ResumeSection> getResumeSectionsOfResume(int resume_id);

    // GET RESUME SECTIONS OF USER
    @Query(
            value = "SELECT rs.* FROM resume_sections rs, resumes r WHERE rs.resume_id = r.id AND r.user_id = ?1",
            nativeQuery = true
    )
    public List<ResumeSection> getResumeSectionsOfUser(int user_id);

}
