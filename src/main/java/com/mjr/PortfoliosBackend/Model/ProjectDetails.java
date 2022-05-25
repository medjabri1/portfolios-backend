package com.mjr.PortfoliosBackend.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mjr.PortfoliosBackend.Utility.StringListConverter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "project_details")
public class ProjectDetails {

    // ATTRIBUTES

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false)
    private Project project;

    @Convert(converter = StringListConverter.class)
    @Column(nullable = true, length = 500)
    protected List<String> contributersList;

    @Convert(converter = StringListConverter.class)
    @Column(nullable = true, length = 500)
    protected List<String> goalsList;

    @Convert(converter = StringListConverter.class)
    @Column(nullable = true, length = 500)
    protected List<String> requirementsList;

    @Convert(converter = StringListConverter.class)
    @Column(nullable = true, length = 500)
    protected List<String> tagsList;

    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Africa/Casablanca")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    // CONSTRUCTORS

    public ProjectDetails() {
    }

    // GETS / SETS

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<String> getContributersList() {
        return contributersList;
    }

    public void setContributersList(List<String> contributersList) {
        this.contributersList = contributersList;
    }

    public List<String> getGoalsList() {
        return goalsList;
    }

    public void setGoalsList(List<String> goalsList) {
        this.goalsList = goalsList;
    }

    public List<String> getRequirementsList() {
        return requirementsList;
    }

    public void setRequirementsList(List<String> requirementsList) {
        this.requirementsList = requirementsList;
    }

    public List<String> getTagsList() {
        return tagsList;
    }

    public void setTagsList(List<String> tagsList) {
        this.tagsList = tagsList;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
