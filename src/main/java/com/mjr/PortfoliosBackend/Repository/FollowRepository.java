package com.mjr.PortfoliosBackend.Repository;

import com.mjr.PortfoliosBackend.Model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Integer> {

    // GET FOLLOW BY ID
    public Follow getById(int id);

    // GET USER FOLLOWERS LIST

    @Query(
            value = "SELECT * FROM follows WHERE followed_id = ?1",
            nativeQuery = true
    )
    public List<Follow> getUserFollowersList(int user_id);

    // GET USER FOLLOWING LIST

    @Query(
            value = "SELECT * FROM follows WHERE follower_id = ?1",
            nativeQuery = true
    )
    public List<Follow> getUserFollowingList(int user_id);

    // CHECk USER FOLLOW

    @Query(
            value = "SELECT * FROM follows WHERE follower_id = ?1 AND followed_id = ?2",
            nativeQuery = true
    )
    public Follow getUserFollowUser(int follower_id, int followed_id);

    // DELETE FOLLOW

    @Query(
            value = "DELETE FROM follows WHERE follower_id = ?1 AND followed_id = ?2",
            nativeQuery = true
    )
    public void deleteFollowByIds(int follower_id, int followed_id);

}
