
package com.freightfox.githubsearcher.client;

import com.freightfox.githubsearcher.dto.GithubApiResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import java.util.List;

@Component
public class GithubApiClient {

    private static final String SEARCH_URL =
            "https://api.github.com/search/repositories";

    private final RestClient restClient;

    public GithubApiClient(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl(SEARCH_URL)
                .defaultHeader("Accept", "application/vnd.github+json")
                .defaultHeader("X-GitHub-Api-Version", "2022-11-28")
                .build();
    }

    public GithubApiResponse searchRepositories(
            String query,
            String language,
            String sort) {

        String searchQuery = query;

        if (language != null && !language.isBlank()) {
        searchQuery = searchQuery + " language:" + language.trim();
        }

        final String finalSearchQuery = searchQuery;

        try {
            GithubApiResponse response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", finalSearchQuery)
                        .queryParam("sort", sort)
                        .queryParam("order", "desc")
                        .queryParam("per_page", 30)
                        .build())
                    .retrieve()
                    .onStatus(
                            HttpStatusCode::isError,
                            (request, clientResponse) -> {
                                throw new GithubApiException(
                                        "GitHub returned HTTP "
                                                + clientResponse.getStatusCode().value());
                            })
                    .body(GithubApiResponse.class);

            if (response == null || response.items() == null) {
                throw new GithubApiException(
                        "GitHub returned an empty or invalid response");
            }

            return response;

        } catch (RestClientResponseException ex) {
            throw new GithubApiException(
                    "GitHub request failed with HTTP "
                            + ex.getStatusCode().value(), ex);
        } catch (RestClientException ex) {
            throw new GithubApiException(
                    "Unable to reach GitHub API", ex);
        }
    }
}
