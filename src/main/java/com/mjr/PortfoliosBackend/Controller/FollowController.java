package com.mjr.PortfoliosBackend.Controller;

import com.mjr.PortfoliosBackend.Model.Follow;
import com.mjr.PortfoliosBackend.Service.FollowService.FollowService;
import com.mjr.PortfoliosBackend.Service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/follow")
public class FollowController {

    @Autowired
    private FollowService followService;

    @Autowired
    private UserService userService;

    // GET ALL FOLLOWS

    @GetMapping("/all-follows")
    public List<Follow> getAllFollows() {
        return followService.getFollows();
    }

    // GET ALL USER FOLLOWERS

    @GetMapping("/user/all-followers")
    public HashMap<String, Object> getUserFollowers(@RequestParam(name = "user_id") int user_id) {

        HashMap<String, Object> response = new HashMap<>();

        if(userService.userExist(user_id)) {
            // USER EXIST
            response.put("status", 1);
            response.put("result", followService.getUserFollowers(user_id));
        } else {
            // USER DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "USER DOESN'T EXIST");
        }

        return response;
    }

    // GET ALL USER FOLLOWINGS

    @GetMapping("/user/all-followings")
    public HashMap<String, Object> getUserFollowings(@RequestParam(name = "user_id") int user_id) {

        HashMap<String, Object> response = new HashMap<>();

        if(userService.userExist(user_id)) {
            // USER EXIST
            response.put("status", 1);
            response.put("result", followService.getUserFollowings(user_id));
        } else {
            // USER DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "USER DOESN'T EXIST");
        }

        return response;
    }

    // GET FOLLOW BY ID

    @GetMapping("/id")
    public HashMap<String, Object> getFollowById(@RequestParam(name = "id") int id) {

        HashMap<String, Object> response = new HashMap<>();

        if(followService.followExist(id)) {
            // FOLLOW EXIST
            response.put("status", 1);
            response.put("follow", followService.getFollow(id));
        } else {
            // FOLLOW DOESN'T EXIST
            response.put("status", 0);
            response.put("error", "FOLLOW DOESN'T EXIST");
        }

        return response;
    }

    // ADD NEW FOLLOW

    @PostMapping("/new")
    public HashMap<String, Object> addNewFollow(@RequestBody Follow follow) {

        HashMap<String, Object> response = new HashMap<>();

        int follower_id = follow.getFollower().getId();
        int followed_id = follow.getFollowed().getId();

        if(follower_id != followed_id) {
            // FOLLOWER AND FOLLOWED ARE DIFFERENT

            if(userService.userExist(follower_id) && userService.userExist(followed_id)) {
                // BOTH USERS EXIST

                if(!followService.followExist(follower_id, followed_id)) {

                    // USER DOESN'T ALREADY FOLLOW USER
                    follow.setCreatedAt(new Date());
                    response.put("status", 1);
                    response.put("follow", followService.saveFollow(follow));

                } else {
                    // USER ALREADY FOLLOW USER
                    response.put("status", 0);
                    response.put("error", "USER ALREADY FOLLOW OTHER USER");
                }


            } else {
                // ONE OF USER OR BOTH DOESN'T EXIST
                response.put("status", 0);
                response.put("error", "ONE OF USER OR BOTH DOESN'T EXIST");
            }

        } else {
            // FOLLOWER AND FOLLOWED ARE THE SAME
            response.put("status", 0);
            response.put("error", "USER CAN'T FOLLOW HIMSELF");
        }

        return response;
    }

    // UPDATE FOLLOW

    // DELETE FOLLOW

    @DeleteMapping("/delete")
    public HashMap<String, Object> deleteFollow(@RequestParam(name = "id") int id) {

        HashMap<String, Object> response = new HashMap<>();

        if(followService.followExist(id)) {
            // FOLLOW EXIST
            followService.deleteFollow(id);
            response.put("status", 1);
        } else {
            // FOLLOW DOESN'T EXIST
            response.put("status", 0);
        }

        return response;
    }
}
