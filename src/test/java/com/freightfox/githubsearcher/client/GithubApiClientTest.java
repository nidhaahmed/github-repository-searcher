
package com.freightfox.githubsearcher.client;

import com.freightfox.githubsearcher.dto.GithubApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.queryParam;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class GithubApiClientTest {

    @Test
    void shouldSearchRepositoriesWithLanguageAndSort() {
        RestClient.Builder builder = RestClient.builder();

        MockRestServiceServer server =
                MockRestServiceServer.bindTo(builder).build();

        GithubApiClient client = new GithubApiClient(builder);

        String responseJson = """
                {
                  "total_count": 1,
                  "items": [
                    {
                      "id": 12345,
                      "name": "spring-project",
                      "description": "A sample repository",
                      "owner": {"login": "example-user"},
                      "language": "Java",
                      "stargazers_count": 250,
                      "forks_count": 40,
                      "updated_at": "2026-10-01T10:00:00Z",
                      "html_url": "https://github.com/example-user/spring-project"
                    }
                  ]
                }
                """;

        server.expect(requestTo(
                        org.hamcrest.Matchers.containsString(
                                "https://api.github.com/search/repositories")))
                .andExpect(method(HttpMethod.GET))
                .andExpect(queryParam("q", "spring%20boot%20language:Java"))
                .andExpect(queryParam("sort", "stars"))
                .andExpect(queryParam("order", "desc"))
                .andExpect(queryParam("per_page", "30"))
                .andRespond(withSuccess(responseJson, MediaType.APPLICATION_JSON));

        GithubApiResponse response =
                client.searchRepositories("spring boot", "Java", "stars");

        assertNotNull(response);
        assertEquals(1, response.totalCount());
        assertEquals("spring-project", response.items().get(0).name());
        assertEquals("example-user", response.items().get(0).owner().login());
        assertEquals(250, response.items().get(0).stars());

        server.verify();
    }
}
