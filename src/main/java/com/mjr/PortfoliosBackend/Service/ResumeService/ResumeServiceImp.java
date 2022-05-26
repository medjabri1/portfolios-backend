package com.mjr.PortfoliosBackend.Service.ResumeService;

import com.mjr.PortfoliosBackend.Model.Resume;
import com.mjr.PortfoliosBackend.Repository.ResumeRepository;
import com.mjr.PortfoliosBackend.Service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResumeServiceImp implements ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private UserService userService;

    @Override
    public Resume saveResume(Resume resume) {
        if(userService.userExist(resume.getUser().getId())) {
            // USER EXIST
            return resumeRepository.save(resume);
        } else {
            // USER DOESN'T EXIST
            return null;
        }
    }

    @Override
    public Resume getResume(int id) {
        return resumeRepository.getById(id);
    }

    @Override
    public List<Resume> getResumes() {
        return resumeRepository.findAll();
    }

    @Override
    public Resume getResumeOfUser(int user_id) {
        return resumeRepository.getByUserId(user_id);
    }

    @Override
    public boolean resumeExist(int id) {
        return resumeRepository.existsById(id);
    }

    @Override
    public boolean resumeOfUserExist(int user_id) {
        return resumeOfUserExist(user_id);
    }

    @Override
    public boolean updateResume(Resume resume) {
        return false;
    }

    @Override
    public boolean deleteResume(int id) {
        if(resumeExist(id)) {
            // RESUME EXIST
            resumeRepository.deleteById(id);
            return true;
        } else {
            // RESUME DOESN'T EXIST
            return false;
        }
    }
}
