package com.mjr.PortfoliosBackend.Repository;

import com.mjr.PortfoliosBackend.Model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    // GET PROJECT BY ID
    public Project getById(int id);

    // GET OWNER PROJECTS
    @Query(
            value = "SELECT * FROM projects WHERE user_id = ?1",
            nativeQuery = true
    )
    public List<Project> getUserProject(int owner_id);

    // GET USER FOLLOWINGS PROJECTS

    @Query(
            value = "SELECT * FROM projects WHERE user_id IN ( SELECT followed_id FROM follows WHERE follower_id = ?1)",
            nativeQuery = true
    )
    public List<Project> getUserFollowingsProjects(int user_id);

}
