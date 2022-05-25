package com.mjr.PortfoliosBackend.Service.ProjectDetailsService;

import com.mjr.PortfoliosBackend.Model.Project;
import com.mjr.PortfoliosBackend.Model.ProjectDetails;
import com.mjr.PortfoliosBackend.Repository.ProjectDetailsRepository;
import com.mjr.PortfoliosBackend.Service.ProjectService.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectDetailsServiceImp implements ProjectDetailsService {

    @Autowired
    private ProjectDetailsRepository projectDetailsRepository;

    @Autowired
    private ProjectDetailsService projectDetailsService;

    @Autowired
    private ProjectService projectService;

    @Override
    public ProjectDetails saveProjectDetails(ProjectDetails projectDetails) {
        if(projectService.projectExist(projectDetails.getProject().getId())) {
            // PROJECT EXISTS
            return projectDetailsRepository.save(projectDetails);
        } else {
            // PROJECT DOESN'T EXIST
            return null;
        }
    }

    @Override
    public ProjectDetails getProjectDetails(int id) {
        if(projectDetailsService.projectDetailsExist(id)) {
            // PROJECT DETAILS EXIST
            return projectDetailsRepository.getById(id);
        } else {
            return null;
        }
    }

    @Override
    public List<ProjectDetails> getProjectsDetails() {
        return projectDetailsRepository.findAll();
    }

    @Override
    public ProjectDetails getDetailsOfProject(int project_id) {
        return projectDetailsRepository.getByProjectId(project_id);
    }

    @Override
    public boolean projectDetailsExist(int id) {
        return projectDetailsRepository.existsById(id);
    }

    @Override
    public boolean updateProjectDetails(ProjectDetails projectDetails) {
        if(projectDetailsExist(projectDetails.getId())) {
            // PROJECT DETAILS EXISTS
            projectDetailsRepository.save(projectDetails);
            return true;
        } else {
            // PROJECT DETAILS DOESN'T EXIST
            return false;
        }
    }

    @Override
    public boolean deleteProjectDetails(int id) {
        if(projectDetailsExist(id)) {
            // PROJECT DETAILS EXISTS
            projectDetailsRepository.deleteById(id);
            return true;
        } else {
            // PROJECT DETAILS DOESN'T EXIST
            return false;
        }
    }
}
