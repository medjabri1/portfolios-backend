package com.mjr.PortfoliosBackend.Service.LikeService;

import com.mjr.PortfoliosBackend.Model.Like;
import com.mjr.PortfoliosBackend.Repository.LikeRepository;
import com.mjr.PortfoliosBackend.Service.ProjectService.ProjectService;
import com.mjr.PortfoliosBackend.Service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeServiceImp implements LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private LikeService likeService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Override
    public Like saveLike(Like like) {

        int user_id = like.getUser().getId();
        int project_id = like.getProject().getId();

        // USER DOESN'T EXIST
        if(!userService.userExist(user_id)) return null;

        // PROJECT DOESN'T EXIST
        if(!projectService.projectExist(project_id)) return null;

        if(likeRepository.getUserLikeProject(user_id, project_id) == null) {
            // USER DOESN'T LIKE PROJECT YET
            return likeRepository.save(like);
        } else {
            // USER ALREADY LIKE PROJECT
            return null;
        }
    }

    @Override
    public Like getLike(int id) {
        return likeRepository.getById(id);
    }

    @Override
    public List<Like> getLikes() {
        return likeRepository.findAll();
    }

    @Override
    public List<Like> getUserLikes(int user_id) {
        return likeRepository.getUserLikes(user_id);
    }

    @Override
    public List<Like> getProjectLikes(int project_id) {
        return likeRepository.getProjectLikes(project_id);
    }

    @Override
    public boolean likeExist(int id) {
        return likeRepository.existsById(id);
    }

    @Override
    public boolean likeExist(int user_id, int project_id) {
        return likeRepository.getUserLikeProject(user_id, project_id) != null;
    }

    @Override
    public boolean updateLike(Like like) {
        return false;
    }

    @Override
    public boolean deleteLike(int id) {
        if(likeExist(id)) {
            // LIKE EXIST
            likeRepository.deleteById(id);
            return true;
        } else {
            // LIKE DOESN'T EXIST
            return false;
        }
    }
}
