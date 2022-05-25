package com.mjr.PortfoliosBackend.Service.ProjectDetailsService;

import com.mjr.PortfoliosBackend.Model.Project;
import com.mjr.PortfoliosBackend.Model.ProjectDetails;

import java.util.List;

public interface ProjectDetailsService {

    // SAVE PROJECT DETAILS
    public ProjectDetails saveProjectDetails(ProjectDetails projectDetails);

    // GET Project DETAILS / Projects DETAILS
    public ProjectDetails getProjectDetails(int id);
    public List<ProjectDetails> getProjectsDetails();

    // GET PROJECT DETAILS BY PROJECT ID
    public ProjectDetails getDetailsOfProject(int project_id);

    // CHECK EXIST
    public boolean projectDetailsExist(int id);

    // UPDATE Project Details
    public boolean updateProjectDetails(ProjectDetails projectDetails);

    // DELETE Project Details
    public boolean deleteProjectDetails(int id);

}
