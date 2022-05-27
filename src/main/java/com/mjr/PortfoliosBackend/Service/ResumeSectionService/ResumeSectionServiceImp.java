package com.mjr.PortfoliosBackend.Service.ResumeSectionService;

import com.mjr.PortfoliosBackend.Model.ResumeSection;
import com.mjr.PortfoliosBackend.Repository.ResumeSectionRepository;
import com.mjr.PortfoliosBackend.Service.ResumeService.ResumeService;
import com.mjr.PortfoliosBackend.Service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResumeSectionServiceImp implements ResumeSectionService {

    @Autowired
    private ResumeSectionRepository resumeSectionRepository;

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private UserService userService;

    @Override
    public ResumeSection saveResumeSection(ResumeSection resumeSection) {
        if(resumeService.resumeExist(resumeSection.getResume().getId())) {
            // RESUME EXIST
            return resumeSectionRepository.save(resumeSection);
        } else {
            // RESUME DOESN'T EXIST
            return null;
        }
    }

    @Override
    public ResumeSection getResumeSection(int id) {
        return resumeSectionRepository.getById(id);
    }

    @Override
    public List<ResumeSection> getResumeSections() {
        return resumeSectionRepository.findAll();
    }

    @Override
    public List<ResumeSection> getUserResumeSections(int user_id) {
        if(userService.userExist(user_id)) {
            // USER EXIST
            return resumeSectionRepository.getResumeSectionsOfUser(user_id);
        } else {
            // USER DOESN'T EXIST
            return null;
        }
    }

    @Override
    public List<ResumeSection> getResumeSectionOfResume(int resume_id) {
        if(resumeService.resumeExist(resume_id)) {
            // RESUME EXIST
            return resumeSectionRepository.getResumeSectionsOfResume(resume_id);
        } else {
            // RESUME DOESN'T EXIST
            return null;
        }
    }

    @Override
    public boolean resumeSectionExist(int id) {
        return resumeSectionRepository.existsById(id);
    }

    @Override
    public boolean updateResumeSection(ResumeSection resumeSection) {
        if(resumeSectionExist(resumeSection.getId())) {
            // RESUME SECTION EXIST
            resumeSectionRepository.save(resumeSection);
            return true;
        } else {
            // RESUME SECTION DOESN'T EXIST
            return false;
        }
    }

    @Override
    public boolean deleteResumeSection(int id) {
        if(resumeSectionExist(id)) {
            // RESUME SECTION EXIST
            resumeSectionRepository.deleteById(id);
            return true;
        } else {
            // RESUME SECTION DOESN'T EXIST
            return false;
        }
    }
}
