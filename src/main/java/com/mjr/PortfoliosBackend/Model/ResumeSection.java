package com.mjr.PortfoliosBackend.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mjr.PortfoliosBackend.Utility.StringListConverter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "resume_sections")
public class ResumeSection {

    // ATTRIBUTES

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "resume_id", referencedColumnName = "id", nullable = false)
    private Resume resume;

    @Column(nullable = false, length = 200)
    private String title;

    @Convert(converter = StringListConverter.class)
    @Column(nullable = true, length = 500)
    protected List<String> content;

    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Africa/Casablanca")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    // CONSTRUCTORS

    public ResumeSection() {
    }

    // GETS / SETS

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
