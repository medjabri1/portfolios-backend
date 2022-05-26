package com.mjr.PortfoliosBackend.Repository;

import com.mjr.PortfoliosBackend.Model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Integer> {

    public Resume getById(int id);

    public Resume getByUserId(int user_id);

}
