package com.mjr.PortfoliosBackend.Service.FavoriteService;

import com.mjr.PortfoliosBackend.Model.Favorite;

import java.util.List;

public interface FavoriteService {

    // SAVE FAVORITE
    public Favorite saveFavorite(Favorite favorite);

    // GET Favorite / Favorites
    public Favorite getFavorite(int id);
    public List<Favorite> getFavorites();

    // GET USER FAVORITES
    public List<Favorite> getUserFavorites(int user_id);

    // GET PROJECT FAVORITES
    public List<Favorite> getProjectFavorites(int project_id);

    // CHECK EXIST
    public boolean favoriteExist(int id);
    public boolean favoriteExist(int user_id, int project_id);

    // UPDATE Favorite
    public boolean updateFavorite(Favorite favorite);

    // DELETE Favorite
    public boolean deleteFavorite(int id);
    
}
