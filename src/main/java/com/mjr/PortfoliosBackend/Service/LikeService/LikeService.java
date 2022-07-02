package com.mjr.PortfoliosBackend.Service.LikeService;

import com.mjr.PortfoliosBackend.Model.Like;

import java.util.List;

public interface LikeService {

    // SAVE LIKE
    public Like saveLike(Like like);

    // GET Like / Likes
    public Like getLike(int id);
    public List<Like> getLikes();

    // GET USER LIKE
    public List<Like> getUserLikes(int user_id);

    // GET PROJECT LIKES
    public List<Like> getProjectLikes(int project_id);

    public Like getUserLikeProject(int user_id, int project_id);

    // CHECK EXIST
    public boolean likeExist(int id);
    public boolean likeExist(int user_id, int project_id);

    // UPDATE Like
    public boolean updateLike(Like like);

    // DELETE Like
    public boolean deleteLike(int id);
    
}
