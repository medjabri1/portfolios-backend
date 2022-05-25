package com.mjr.PortfoliosBackend.Repository;

import com.mjr.PortfoliosBackend.Model.ProjectDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectDetailsRepository extends JpaRepository<ProjectDetails, Integer> {

    public ProjectDetails getById(int id);

    /*@Query(
            value = "SELECT * FROM project_details WHERE project_id = ?1 AND ROWNUM <= 1",
            nativeQuery = true
    )*/
    public ProjectDetails getByProjectId(int project_id);

}
