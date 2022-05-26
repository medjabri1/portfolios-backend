package com.mjr.PortfoliosBackend.Controller;

import com.mjr.PortfoliosBackend.Model.Favorite;
import com.mjr.PortfoliosBackend.Model.Like;
import com.mjr.PortfoliosBackend.Service.FavoriteService.FavoriteService;
import com.mjr.PortfoliosBackend.Service.ProjectService.ProjectService;
import com.mjr.PortfoliosBackend.Service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/favorite")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    // GET ALL FAVORITES

    @GetMapping("/all-favorites")
    public List<Favorite> getAllFavorites() {
        return favoriteService.getFavorites();
    }

    // GET USER ALL FAVORITES

    @GetMapping("/user")
    public HashMap<String, Object> getUserFavorites(@RequestParam(name = "user_id") int user_id) {

        HashMap<String, Object> response = new HashMap<>();

        if(userService.userExist(user_id)) {
            // USER EXIST
            response.put("status", 1);
            response.put("result", favoriteService.getUserFavorites(user_id));
        } else {
            // USER DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "USER DOESN'T EXIST");
        }

        return response;
    }

    // GET PROJECT FAVORITES

    @GetMapping("/project")
    public HashMap<String, Object> getProjectFavorites(@RequestParam(name = "project_id") int project_id) {

        HashMap<String, Object> response = new HashMap<>();

        if(projectService.projectExist(project_id)) {
            // PROJECT EXIST
            response.put("status", 1);
            response.put("result", favoriteService.getProjectFavorites(project_id));
        } else {
            // PROJECT DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "PROJECT DOESN'T EXIST");
        }

        return response;
    }

    // GET PROJECT BY ID

    @GetMapping("/id")
    public HashMap<String, Object> getFavoriteByID(@RequestParam(name = "id") int id) {

        HashMap<String, Object> response = new HashMap<>();

        if(favoriteService.favoriteExist(id)) {
            // FAVORITE EXIST
            response.put("status", 1);
            response.put("result", favoriteService.getFavorite(id));
        } else {
            // FAVORITE DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "FAVORITE DOESN'T EXIST");
        }

        return response;
    }

    // ADD NEW FAVORITE

    @PostMapping("/new")
    public HashMap<String, Object> addNewFavorite(@RequestBody Favorite favorite) {

        HashMap<String, Object> response = new HashMap<>();

        int user_id = favorite.getUser().getId();
        int project_id = favorite.getProject().getId();

        if(userService.userExist(user_id)) {
            // USER EXIST

            if(projectService.projectExist(project_id)) {
                // PROJECT EXIST

                if(!favoriteService.favoriteExist(user_id, project_id)) {
                    // USER DOESN'T FAVORITE PROJECT YET

                    favorite.setCreatedAt(new Date());

                    response.put("status", 1);
                    response.put("favorite", favoriteService.saveFavorite(favorite));

                } else {
                    // USER ALREADY FAVORITE PROJECT
                    response.put("status", 0);
                    response.put("error", "USER ALREADY FAVORITE PROJECT");
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

    // UPDATE FAVORITE

    @PutMapping("/update")
    public HashMap<String, Object> updateFavorite(@RequestBody Favorite favorite) {

        HashMap<String, Object> response = new HashMap<>();

        response.put("status", 0);
        response.put("error", "THIS METHOD NOT IMPLEMENTED IN BACKEND");

        return response;
    }

    // DELETE FAVORITE

    @DeleteMapping("/delete")
    public HashMap<String, Object> deleteFavorite(@RequestParam(name = "id") int id) {

        HashMap<String, Object> response = new HashMap<>();

        if(favoriteService.favoriteExist(id)) {
            // FAVORITE EXIST
            favoriteService.deleteFavorite(id);
            response.put("status", 1);

        } else {
            // FAVORITE DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "FAVORITE DOESN'T EXIST");
        }

        return response;
    }


}
