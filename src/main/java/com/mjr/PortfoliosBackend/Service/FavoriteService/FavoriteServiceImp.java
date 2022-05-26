package com.mjr.PortfoliosBackend.Service.FavoriteService;

import com.mjr.PortfoliosBackend.Model.Favorite;
import com.mjr.PortfoliosBackend.Repository.FavoriteRepository;
import com.mjr.PortfoliosBackend.Service.ProjectService.ProjectService;
import com.mjr.PortfoliosBackend.Service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteServiceImp implements FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Override
    public Favorite saveFavorite(Favorite favorite) {

        int user_id = favorite.getUser().getId();
        int project_id = favorite.getProject().getId();

        // USER DOESN'T EXIST
        if(!userService.userExist(user_id)) return null;

        // PROJECT DOESN'T EXIST
        if(!projectService.projectExist(project_id)) return null;

        if(favoriteRepository.getUserFavoriteProject(user_id, project_id) == null) {
            // USER DOESN'T FAVORITE PROJECT YET
            return favoriteRepository.save(favorite);
        } else {
            // USER ALREADY FAVORITE PROJECT
            return null;
        }
    }

    @Override
    public Favorite getFavorite(int id) {
        return favoriteRepository.getById(id);
    }

    @Override
    public List<Favorite> getFavorites() {
        return favoriteRepository.findAll();
    }

    @Override
    public List<Favorite> getUserFavorites(int user_id) {
        return favoriteRepository.getUserFavorites(user_id);
    }

    @Override
    public List<Favorite> getProjectFavorites(int project_id) {
        return favoriteRepository.getProjectFavorites(project_id);
    }

    @Override
    public boolean favoriteExist(int id) {
        return favoriteRepository.existsById(id);
    }

    @Override
    public boolean favoriteExist(int user_id, int project_id) {
        return favoriteRepository.getUserFavoriteProject(user_id, project_id) != null;
    }

    @Override
    public boolean updateFavorite(Favorite favorite) {
        return false;
    }

    @Override
    public boolean deleteFavorite(int id) {
        if(favoriteExist(id)) {
            // FAVORITE EXIST
            favoriteRepository.deleteById(id);
            return true;
        } else {
            // FAVORITE DOESN'T EXIST
            return false;
        }
    }
}
