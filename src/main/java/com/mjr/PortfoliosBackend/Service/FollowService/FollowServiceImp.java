package com.mjr.PortfoliosBackend.Service.FollowService;

import com.mjr.PortfoliosBackend.Model.Follow;
import com.mjr.PortfoliosBackend.Repository.FollowRepository;
import com.mjr.PortfoliosBackend.Service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowServiceImp implements FollowService {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private FollowService followService;

    @Autowired
    private UserService userService;

    @Override
    public Follow saveFollow(Follow follow) {

        int follower_id = follow.getFollower().getId();
        int followed_id = follow.getFollowed().getId();

        if(userService.userExist(follower_id) && userService.userExist(followed_id)) {
            // USER EXIST
            return followRepository.save(follow);
        } else {
            // USER DOESN'T EXIST
            return null;
        }
    }

    @Override
    public Follow getFollow(int id) {
        return followRepository.getById(id);
    }

    @Override
    public List<Follow> getFollows() {
        return followRepository.findAll();
    }

    @Override
    public List<Follow> getUserFollowers(int user_id) {
        if(userService.userExist(user_id)) {
            // USER EXIST
            return followRepository.getUserFollowersList(user_id);
        } else {
            // USER DOESN'T EXIST
            return null;
        }
    }

    @Override
    public List<Follow> getUserFollowings(int user_id) {
        if(userService.userExist(user_id)) {
            // USER EXIST
            return followRepository.getUserFollowingList(user_id);
        } else {
            // USER DOESN'T EXIST
            return null;
        }
    }

    @Override
    public boolean followExist(int id) {
        return followRepository.existsById(id);
    }

    @Override
    public boolean followExist(int follower_id, int followed_id) {
        return followRepository.getUserFollowUser(follower_id, followed_id) != null;
    }

    @Override
    public boolean updateFollow(Follow follow) {
        return false;
    }

    @Override
    public boolean deleteFollow(int id) {
        if(followExist(id)) {
            // FOLLOW EXIST
            followRepository.deleteById(id);
            return true;
        } else {
            // FOLLOW DOESN'T EXIST
            return false;
        }
    }
}
