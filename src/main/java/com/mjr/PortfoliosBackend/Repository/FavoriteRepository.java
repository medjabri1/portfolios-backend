package com.mjr.PortfoliosBackend.Repository;

import com.mjr.PortfoliosBackend.Model.Favorite;
import com.mjr.PortfoliosBackend.Model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {

    // GET FAVORITE BY ID
    public Favorite getById(int id);

    // GET PROJECT FAVORITES
    @Query(
            value = "SELECT * FROM favorites WHERE project_id = ?1 ORDER BY created_at DESC",
            nativeQuery = true
    )
    public List<Favorite> getProjectFavorites(int project_id);

    // GET USER FAVORITES
    @Query(
            value = "SELECT * FROM favorites WHERE user_id = ?1 ORDER BY created_at DESC",
            nativeQuery = true
    )
    public List<Favorite> getUserFavorites(int user_id);

    // GET USER FAVORITE PROJECT
    @Query(
            value = "SELECT * FROM favorites WHERE user_id = ?1 AND project_id = ?2 AND ROWNUM <= 1",
            nativeQuery = true
    )
    public Favorite getUserFavoriteProject(int user_id, int project_id);

}
