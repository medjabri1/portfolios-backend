package com.mjr.PortfoliosBackend.Controller;

import com.mjr.PortfoliosBackend.Model.ProjectDetails;
import com.mjr.PortfoliosBackend.Service.ProjectDetailsService.ProjectDetailsService;
import com.mjr.PortfoliosBackend.Service.ProjectService.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/project-details")
public class ProjectDetailsController {

    @Autowired
    private ProjectDetailsService projectDetailsService;

    @Autowired
    private ProjectService projectService;

    // GET LIST OF ALL DETAILS

    @GetMapping("/all-details")
    public List<ProjectDetails> getAllDetails() {
        return projectDetailsService.getProjectsDetails();
    }

    // GET PROJECT DETAILS BY DETAILS ID

    @GetMapping("/id/")
    public HashMap<String, Object> getDetailsById(@RequestParam(name = "id") int id) {

        HashMap<String, Object> response = new HashMap<>();

        if(projectDetailsService.projectDetailsExist(id)) {
            // PROJECT DETAILS EXISTS
            response.put("status", 1);
            response.put("details", projectDetailsService.getProjectDetails(id));
        } else {
            // PROJECT DETAILS DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "PROJECT DETAILS DOESN'T EXIST");
        }

        return response;

    }

    // GET DETAILS OF PROJECT

    @GetMapping("/project/")
    public HashMap<String, Object> getDetailsOfProject(@RequestParam(name = "project_id") int project_id) {

        HashMap<String, Object> response = new HashMap<>();

        if(projectDetailsService.getDetailsOfProject(project_id) != null) {
            // PROJECT DETAILS EXISTS
            response.put("status", 1);
            response.put("details", projectDetailsService.getDetailsOfProject(project_id));
        } else {
            // PROJECT DETAILS DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "PROJECT DETAILS DOESN'T EXIST");
        }

        return response;
    }

    // ADD NEW PROJECT DETAILS

    @PutMapping("/new")
    public HashMap<String, Object> addNewProjectDetails(@RequestBody ProjectDetails projectDetails) {

        HashMap<String, Object> response = new HashMap<>();

        if(projectDetailsService.getDetailsOfProject(projectDetails.getProject().getId()) != null) {
            // PROJECT DETAILS ALREADY EXISTS
            response.put("status", 0);
            response.put("error", "PROJECT DETAILS ALREADY EXISTS");

        } else {

            if(projectService.projectExist(projectDetails.getProject().getId())) {
                // PROJECT EXISTS
                projectDetails.setCreatedAt(new Date());

                response.put("status", 1);
                response.put("details", projectDetailsService.saveProjectDetails(projectDetails));
            } else {
                // PROJECT DOESN'T EXIST
                response.put("status", 0);
                response.put("error", "PROJECT DOESN'T EXIST");
            }
        }

        return response;
    }

    // UPDATE PROJECT DETAILS

    @PostMapping("/update")
    public HashMap<String, Object> updateProjectDetails(@RequestBody ProjectDetails projectDetails) {

        HashMap<String, Object> response = new HashMap<>();

        if(projectDetailsService.projectDetailsExist(projectDetails.getId())) {
            // PROJECT DETAILS EXISTS
            ProjectDetails initial_projectDetails = projectDetailsService.getProjectDetails(projectDetails.getId());

            projectDetails.setCreatedAt(initial_projectDetails.getCreatedAt());

            if(projectDetails.getContributersList() == null)
                projectDetails.setContributersList(initial_projectDetails.getContributersList());

            if(projectDetails.getGoalsList() == null)
                projectDetails.setGoalsList(initial_projectDetails.getGoalsList());

            if(projectDetails.getRequirementsList() == null)
                projectDetails.setRequirementsList(initial_projectDetails.getRequirementsList());

            if(projectDetails.getTagsList() == null)
                projectDetails.setTagsList(initial_projectDetails.getTagsList());

            projectDetails.setProject(initial_projectDetails.getProject());

            response.put("status", 1);
            response.put("details", projectDetailsService.saveProjectDetails(projectDetails));
        } else {
            // PROJECT DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "PROJECT DOESN'T EXIST");
        }

        return response;

    }

    // DELETE PROJECT DETAILS

    @DeleteMapping("/delete")
    public HashMap<String, Object> deleteProjectDetails(@RequestParam(name = "id") int id) {

        HashMap<String, Object> response = new HashMap<>();

        if(projectDetailsService.projectDetailsExist(id)) {
            // PROJECT DETAILS EXISTS
            response.put("status", 1);
            response.put("details", projectDetailsService.deleteProjectDetails(id));
        } else {
            // PROJECT DETAILS DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "PROJECT DETAILS DOESN'T EXIST");
        }

        return response;

    }

}
