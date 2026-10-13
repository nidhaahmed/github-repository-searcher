
package com.freightfox.githubsearcher.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "github_repositories")
public class GithubRepository {

    @Id
    @Column(name = "github_id", nullable = false)
    private Long id;

    @Column(name = "repo_name", nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "owner_name", nullable = false)
    private String owner;

    @Column(name = "programming_language")
    private String language;

    @Column(name = "stars_count", nullable = false)
    private Integer stars;

    @Column(name = "forks_count", nullable = false)
    private Integer forks;

    @Column(name = "github_updated_at")
    private Instant lastUpdated;

    protected GithubRepository() {
        // Required by JPA
    }

    public GithubRepository(
            Long id,
            String name,
            String description,
            String owner,
            String language,
            Integer stars,
            Integer forks,
            Instant lastUpdated) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.language = language;
        this.stars = stars;
        this.forks = forks;
        this.lastUpdated = lastUpdated;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getOwner() {
        return owner;
    }

    public String getLanguage() {
        return language;
    }

    public Integer getStars() {
        return stars;
    }

    public Integer getForks() {
        return forks;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public void setForks(Integer forks) {
        this.forks = forks;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
