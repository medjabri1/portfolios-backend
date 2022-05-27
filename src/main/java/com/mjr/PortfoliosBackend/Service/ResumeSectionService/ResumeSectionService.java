package com.mjr.PortfoliosBackend.Service.ResumeSectionService;

import com.mjr.PortfoliosBackend.Model.Like;
import com.mjr.PortfoliosBackend.Model.ResumeSection;

import java.util.List;

public interface ResumeSectionService {

    // SAVE ResumeSection
    public ResumeSection saveResumeSection(ResumeSection resumeSection);

    // GET ResumeSection / ResumeSections
    public ResumeSection getResumeSection(int id);
    public List<ResumeSection> getResumeSections();

    // GET USER ResumeSections
    public List<ResumeSection> getUserResumeSections(int user_id);

    // GET RESUME Sections BY RESUME ID
    public List<ResumeSection> getResumeSectionOfResume(int resume_id);

    // CHECK EXIST
    public boolean resumeSectionExist(int id);

    // UPDATE ResumeSection
    public boolean updateResumeSection(ResumeSection resumeSection);

    // DELETE ResumeSection
    public boolean deleteResumeSection(int id);

}
