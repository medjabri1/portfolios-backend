package com.mjr.PortfoliosBackend.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "resumes")
public class Resume {

    // ATTRIBUTES

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(nullable = true, length = 500)
    private String biography;

    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Africa/Casablanca")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    // CONSTRUCTORS

    public Resume() {
    }

    // GETS / SETS

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
