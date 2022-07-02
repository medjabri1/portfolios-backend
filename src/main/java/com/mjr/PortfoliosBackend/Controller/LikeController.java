package com.mjr.PortfoliosBackend.Controller;

import com.mjr.PortfoliosBackend.Model.Like;
import com.mjr.PortfoliosBackend.Service.LikeService.LikeService;
import com.mjr.PortfoliosBackend.Service.ProjectService.ProjectService;
import com.mjr.PortfoliosBackend.Service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/like")
@CrossOrigin(origins = { "http://localhost:3000" }, allowedHeaders = "*", allowCredentials = "true")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    // GET LIST OF ALL LIKES

    @GetMapping("/all-likes")
    public List<Like> getAllLikes() {
        return likeService.getLikes();
    }

    // GET ALL USER LIKES

    @GetMapping("/user")
    public HashMap<String, Object> getUserLikes(@RequestParam(name = "user_id") int user_id) {

        HashMap<String, Object> response = new HashMap<>();

        if(userService.userExist(user_id)) {
            // USER EXIST

            response.put("status", 1);
            response.put("result", likeService.getUserLikes(user_id));

        } else {
            // USER DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "USER DOESN'T EXIST");
        }

        return response;
    }

    // GET ALL PROJECT LIKES

    @GetMapping("/project")
    public HashMap<String, Object> getrProjectLikes(@RequestParam(name = "project_id") int project_id) {

        HashMap<String, Object> response = new HashMap<>();

        if(projectService.projectExist(project_id)) {
            // PROJECT EXIST

            response.put("status", 1);
            response.put("result", likeService.getProjectLikes(project_id));

        } else {
            // USER DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "PROJECT DOESN'T EXIST");
        }

        return response;
    }

    // CHECK LIKE

    @GetMapping("/exist")
    public HashMap<String, Object> checkUserLikeProject(
            @RequestParam(name = "project_id") int project_id,
            @RequestParam(name = "user_id") int user_id) {

        HashMap<String, Object> response = new HashMap<>();

        if(likeService.likeExist(user_id, project_id)) {
            // LIKE EXIST
            response.put("status", 1);
        } else {
            // LIKE DOESN'T EXIST
            response.put("status", 0);
        }

        return response;
    }

    // GET LIKE BY ID

    @GetMapping("/id")
    public HashMap<String, Object> getLikeById(@RequestParam(name = "id") int id) {

        HashMap<String, Object> response = new HashMap<>();

        if(likeService.likeExist(id)) {
            // LIKE EXIST

            response.put("status", 1);
            response.put("result", likeService.getLike(id));

        } else {
            // LIKE DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "LIKE DOESN'T EXIST");
        }

        return response;
    }

    // ADD NEW LIKE

    @PostMapping("/new")
    public HashMap<String, Object> addNewLike(@RequestBody Like like) {

        HashMap<String, Object> response = new HashMap<>();

        int user_id = like.getUser().getId();
        int project_id = like.getProject().getId();

        if(userService.userExist(user_id)) {
            // USER EXIST

            if(projectService.projectExist(project_id)) {
                // PROJECT EXIST

                if(!likeService.likeExist(user_id, project_id)) {
                    // USER DOESN'T LIKE PROJECT YET

                    like.setCreatedAt(new Date());

                    response.put("status", 1);
                    response.put("like", likeService.saveLike(like));

                } else {
                    // USER ALREADY LIKE PROJECT
                    response.put("status", 0);
                    response.put("error", "USER ALREADY LIKE PROJECT");
                }

            } else {
                // PROJECT DOESN'T EXIST
                response.put("status", 0);
                response.put("error", "PROJECT DOESN'T EXIST");
            }

        } else {
            // USER DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "USER DOESN'T EXIST");
        }

        return response;
    }

    // UPDATE LIKE

    @PutMapping("/update")
    public HashMap<String, Object> updateLike(@RequestBody Like like) {

        HashMap<String, Object> response = new HashMap<>();

        response.put("status", 0);
        response.put("error", "THIS METHOD NOT IMPLEMENTED IN BACKEND");

        return response;
    }

    // DELETE LIKE

    @DeleteMapping("/delete")
    public HashMap<String, Object> deleteLike(@RequestParam(name = "id") int id) {

        HashMap<String, Object> response = new HashMap<>();

        if(likeService.likeExist(id)) {
            // LIKE EXIST
            likeService.deleteLike(id);
            response.put("status", 1);

        } else {
            // LIKE DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "LIKE DOESN'T EXIST");
        }

        return response;
    }

    // DELETE USER LIKE PROJECT

    @DeleteMapping("/delete-user-like")
    public HashMap<String, Object> deleteUserLike(
            @RequestParam(name = "user_id") int user_id,
            @RequestParam(name = "project_id") int project_id) {

        HashMap<String, Object> response = new HashMap<>();

        if(likeService.likeExist(user_id, project_id)) {
            // LIKE EXIST
            likeService.deleteLike(likeService.getUserLikeProject(user_id, project_id).getId());
            response.put("status", 1);

        } else {
            // LIKE DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "LIKE DOESN'T EXIST");
        }

        return response;
    }

}
