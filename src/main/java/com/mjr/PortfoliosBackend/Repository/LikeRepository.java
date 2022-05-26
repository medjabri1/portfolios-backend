package com.mjr.PortfoliosBackend.Repository;

import com.mjr.PortfoliosBackend.Model.Like;
import com.mjr.PortfoliosBackend.Model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {

    // GET LIKE BY ID
    public Like getById(int id);

    // GET PROJECT LIKES
    @Query(
            value = "SELECT * FROM likes WHERE project_id = ?1 ORDER BY created_at DESC",
            nativeQuery = true
    )
    public List<Like> getProjectLikes(int project_id);

    // GET USER LIKES
    @Query(
            value = "SELECT * FROM likes WHERE user_id = ?1 ORDER BY created_at DESC",
            nativeQuery = true
    )
    public List<Like> getUserLikes(int user_id);

    // GET USER LIKE PROJECT
    @Query(
            value = "SELECT * FROM likes WHERE user_id = ?1 AND project_id = ?2 AND ROWNUM <= 1",
            nativeQuery = true
    )
    public Like getUserLikeProject(int user_id, int project_id);

}
