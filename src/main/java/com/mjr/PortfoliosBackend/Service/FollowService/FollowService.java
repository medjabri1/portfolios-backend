package com.mjr.PortfoliosBackend.Service.FollowService;

import com.mjr.PortfoliosBackend.Model.Follow;

import java.util.List;

public interface FollowService {

    // SAVE FOLLOW
    public Follow saveFollow(Follow follow);

    // GET Follow / Follows
    public Follow getFollow(int id);
    public List<Follow> getFollows();

    // GET USER FOLLOWERS
    public List<Follow> getUserFollowers(int user_id);

    // GET USER FOLLOWINGS
    public List<Follow> getUserFollowings(int user_id);

    // CHECK EXIST
    public boolean followExist(int id);
    public boolean followExist(int follower_id, int followed_id);

    // UPDATE Follow
    public boolean updateFollow(Follow follow);

    // DELETE Follow
    public boolean deleteFollow(int id);

    // DELETE FOLLOW
    public boolean deleteFollow(int follower_id, int followed_id);

}
