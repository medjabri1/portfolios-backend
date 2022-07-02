package com.mjr.PortfoliosBackend.Service.ProjectService;

import com.mjr.PortfoliosBackend.Model.Project;
import com.mjr.PortfoliosBackend.Repository.ProjectRepository;
import com.mjr.PortfoliosBackend.Service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImp implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Override
    public Project saveProject(Project project) {
        if(userService.userExist(project.getOwner().getId())) {
            // USER OWNER EXIST
            return projectRepository.save(project);
        } else {
            // USER OWNER DOESN'T EXIST
            return null;
        }
    }

    @Override
    public Project getProject(int id) {
        if(projectService.projectExist(id)) {
            // PROJECT EXIST
            return projectRepository.getById(id);
        } else {
            return null;
        }
    }

    @Override
    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    @Override
    public List<Project> getUserProjects(int user_id) {
        // GET USER PROJECTS
        return projectRepository.getUserProject(user_id);
    }

    @Override
    public List<Project> getUserFollowingsProjects(int user_id) {
        return projectRepository.getUserFollowingsProjects(user_id);
    }

    @Override
    public boolean projectExist(int id) {
        return projectRepository.existsById(id);
    }

    @Override
    public boolean updateProject(Project project) {

        if(projectExist(project.getId())) {
            // PROJECT EXISTS
            projectRepository.save(project);
            return true;
        } else {
            //PROJECT DOESN'T EXIST
            return false;
        }
    }

    @Override
    public boolean deleteProject(int id) {

        if(projectExist(id)) {
            // PROJECT EXISTS
            projectRepository.deleteById(id);
            return true;
        } else {
            //PROJECT DOESN'T EXIST
            return false;
        }
    }
}
