package com.mjr.PortfoliosBackend.Controller;

import com.mjr.PortfoliosBackend.Model.Follow;
import com.mjr.PortfoliosBackend.Model.Project;
import com.mjr.PortfoliosBackend.Model.User;
import com.mjr.PortfoliosBackend.Service.FavoriteService.FavoriteService;
import com.mjr.PortfoliosBackend.Service.FollowService.FollowService;
import com.mjr.PortfoliosBackend.Service.LikeService.LikeService;
import com.mjr.PortfoliosBackend.Service.ProjectService.ProjectService;
import com.mjr.PortfoliosBackend.Service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/project")
@CrossOrigin(origins = { "http://localhost:3000" }, allowedHeaders = "*", allowCredentials = "true")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private FollowService followService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private FavoriteService favoriteService;

    // GET LIST OF ALL PROJECTS

    @GetMapping("/all-projects")
    public List<Project> getAllProjects() {
        return projectService.getProjects();
    }

    // GET ONE PROJECT BY ID

    @GetMapping("/id/")
    public HashMap<String, Object> getProjectById(@RequestParam(name = "id") int id) {

        HashMap<String, Object> response = new HashMap<>();

        if(projectService.projectExist(id)) {
            // PROJECT EXISTS
            response.put("status", 1);
            response.put("project", projectService.getProject(id));
        } else {
            // PROJECT DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "PROJECT DOESN'T EXIST");
        }

        return response;
    }

    // GET USER PROJECTS BY USER ID

    @GetMapping("/user/")
    public HashMap getUserProjects(@RequestParam(name = "user_id") int user_id) {

        HashMap<String, Object> response = new HashMap<>();

        if(userService.userExist(user_id)) {
            // USER EXIST

            response.put("status", 1);
            response.put("result", projectService.getUserProjects(user_id));

        } else {
            // USER DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "USER DOESN'T EXIST");
        }

        return response;
    }

    // GET USER FOLLOWINGS PROJECTS BY USER ID

    @GetMapping("/user/following/")
    public HashMap<String, Object> getUserFollowingProjects(@RequestParam(name = "user_id") int user_id) {

        HashMap<String, Object> response = new HashMap<>();

        if(userService.userExist(user_id)) {
            // USER EXISTS

            response.put("status", 1);
            response.put("result", projectService.getUserFollowingsProjects(user_id));

        } else {
            // USER DOESN'T EXIST

            response.put("status", 0);
            response.put("error", "USER DOESN'T EXIST");
        }

        return response;

    }

    // GET PROJECT STATS

    @GetMapping("/stats")
    public HashMap<String, Object> getProjectStats(@RequestParam(name = "id") int project_id) {

        HashMap<String, Object> response = new HashMap<>();

        if(projectService.projectExist(project_id)) {
            // PROJECT EXISTS

            int likes = likeService.getProjectLikes(project_id).size();
            int favorites = favoriteService.getProjectFavorites(project_id).size();

            response.put("status", 1);
            response.put("likes", likes);
            response.put("favorites", favorites);

        } else {
            // PROJECT DOESN'T EXIST

            response.put("status", 0);
            response.put("error", "PROJECT DOESN'T EXIST");
        }

        return response;

    }

    // ADD NEW PROJECT

    @PostMapping("/new")
    public HashMap<String, Object> addNewProject(@RequestBody Project project) {

        HashMap<String, Object> response = new HashMap<>();

        if(userService.userExist(project.getOwner().getId())) {
            // USER EXIST
            project.setCreatedAt(new Date());
            response.put("status", 1);
            response.put("project", projectService.saveProject(project));
        } else {
            // USER DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "USER DOESN'T EXIST");
        }

        return response;
    }

    // UPDATE PROJECT

    @PutMapping("/update")
    public HashMap<String, Object> updateProject(@RequestBody Project project) {

        HashMap<String, Object> response = new HashMap<>();

        if(projectService.projectExist(project.getId())) {
            // PROJECT EXIST
            project.setCreatedAt(projectService.getProject(project.getId()).getCreatedAt());
            project.setOwner(projectService.getProject(project.getId()).getOwner());

            projectService.updateProject(project);

            response.put("status", 1);
            response.put("project", projectService.getProject(project.getId()));
        } else {
            // PROJECT DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "PROJECT DOESN'T EXIST");
        }

        return response;
    }

    // DELETE PROJECT

    @DeleteMapping("/delete")
    public HashMap<String, Object> deleteProject(@RequestParam(name = "id") int id) {

        HashMap<String, Object> response = new HashMap<>();

        if(projectService.projectExist(id)) {
            // PROJECT EXIST
            response.put("status", 1);
            response.put("project", projectService.deleteProject(id));
        } else {
            // PROJECT DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "PROJECT DOESN'T EXIST");
        }

        return response;

    }

}
