package com.mjr.PortfoliosBackend.Service.ResumeService;

import com.mjr.PortfoliosBackend.Model.Project;
import com.mjr.PortfoliosBackend.Model.Resume;

import java.util.List;

public interface ResumeService {

    // SAVE RESUME 
    public Resume saveResume(Resume resume);

    // GET Resume  / Resumes 
    public Resume getResume(int id);
    public List<Resume> getResumes();
    public Resume getResumeOfUser(int user_id);

    // CHECK EXIST
    public boolean resumeExist(int id);
    public boolean resumeOfUserExist(int user_id);

    // UPDATE Resume 
    public boolean updateResume(Resume resume);

    // DELETE Resume 
    public boolean deleteResume(int id);

}
