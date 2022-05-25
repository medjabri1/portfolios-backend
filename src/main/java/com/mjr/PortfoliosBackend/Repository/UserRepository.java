package com.mjr.PortfoliosBackend.Repository;

import com.mjr.PortfoliosBackend.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    public User getById(int id);

    public User getByEmail(String email);

}
