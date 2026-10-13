
package com.freightfox.githubsearcher.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.List;

public record GithubApiResponse(
        @JsonProperty("total_count") int totalCount,
        List<GithubApiRepository> items) {

    public record GithubApiRepository(
            Long id,
            String name,
            String description,
            GithubOwner owner,
            String language,
            @JsonProperty("stargazers_count") Integer stars,
            @JsonProperty("forks_count") Integer forks,
            @JsonProperty("updated_at") Instant lastUpdated,
            @JsonProperty("html_url") String htmlUrl) {
    }

    public record GithubOwner(String login) {
    }
}
