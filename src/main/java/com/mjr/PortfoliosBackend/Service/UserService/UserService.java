package com.mjr.PortfoliosBackend.Service.UserService;

import com.mjr.PortfoliosBackend.Model.User;

import java.util.List;

public interface UserService {

    // SAVE USER
    public User saveUser(User user);

    // GET USER / USERS
    public User getUser(int id);
    public User getUserByEmail(String email);
    public List<User> getUsers();

    // CHECK EXIST
    public boolean userExist(int id);
    public boolean userExistEmail(String email);

    // UPDATE USER
    public boolean updateUser(User user);

    // DELETE USER
    public boolean deleteUser(int id);

}
