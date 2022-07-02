package com.mjr.PortfoliosBackend.Service.ProjectService;

import com.mjr.PortfoliosBackend.Model.Project;
import com.mjr.PortfoliosBackend.Model.User;

import java.util.List;

public interface ProjectService {

    // SAVE PROJECT
    public Project saveProject(Project project);

    // GET Project / Projects
    public Project getProject(int id);
    public List<Project> getProjects();

    // GET USER PROJECT
    public List<Project> getUserProjects(int user_id);

    // GET USER FOLLOWINGS PROJECTS
    public List<Project> getUserFollowingsProjects(int user_id);

    // CHECK EXIST
    public boolean projectExist(int id);

    // UPDATE Project
    public boolean updateProject(Project project);

    // DELETE Project
    public boolean deleteProject(int id);

}
