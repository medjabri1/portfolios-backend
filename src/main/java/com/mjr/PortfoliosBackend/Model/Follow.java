package com.mjr.PortfoliosBackend.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "follows")
public class Follow {

    // ATTRIBUTES

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="follower_id", referencedColumnName = "id", nullable = false)
    private User follower;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="followed_id", referencedColumnName = "id", nullable = false)
    private User followed;

    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Africa/Casablanca")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    // CONSTRUCTORS

    public Follow() {
    }

    // GETS / SETS

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public User getFollowed() {
        return followed;
    }

    public void setFollowed(User followed) {
        this.followed = followed;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
