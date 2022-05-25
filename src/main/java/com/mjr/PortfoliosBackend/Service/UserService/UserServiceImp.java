package com.mjr.PortfoliosBackend.Service.UserService;

import com.mjr.PortfoliosBackend.Model.User;
import com.mjr.PortfoliosBackend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public User saveUser(User user) {
        if(userRepository.getByEmail(user.getEmail()) == null) {
            // EMAIL AVAILABLE
            return userRepository.save(user);
        } else {
            // EMAIL ALREADY TAKEN
            return new User();
        }
    }

    @Override
    public User getUser(int id) {

        return userRepository.getById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean userExist(int id) {
        //return userService.getUser(id) != null ? true : false;
        return userRepository.existsById(id);
    }

    @Override
    public boolean userExistEmail(String email) {
        return userRepository.getByEmail(email) != null;
    }

    @Override
    public boolean updateUser(User user) {

        if(userExist(user.getId())) {
            // USER EXISTS
            return userRepository.save(user) != null;
        } else {
            //USER DOESN'T EXIST
            return false;
        }
    }

    @Override
    public boolean deleteUser(int id) {

        if(userExist(id)) {
            // USER EXISTS
            userRepository.deleteById(id);
            return true;
        } else {
            //USER DOESN'T EXIST
            return false;
        }
    }

}
