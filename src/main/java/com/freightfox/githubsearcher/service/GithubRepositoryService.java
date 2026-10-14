
package com.freightfox.githubsearcher.service;

import com.freightfox.githubsearcher.client.GithubApiClient;
import com.freightfox.githubsearcher.dto.GithubApiResponse;
import com.freightfox.githubsearcher.entity.GithubRepository;
import com.freightfox.githubsearcher.repository.GithubRepositoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GithubRepositoryService {

    private final GithubApiClient githubApiClient;
    private final GithubRepositoryRepository repository;

    public GithubRepositoryService(
            GithubApiClient githubApiClient,
            GithubRepositoryRepository repository) {
        this.githubApiClient = githubApiClient;
        this.repository = repository;
    }

    @Transactional
    public List<GithubRepository> searchRepositories(
            String query, String language, String sort) {

        GithubApiResponse response =
                githubApiClient.searchRepositories(query, language, sort);

        List<GithubRepository> repositories = response.items().stream()
                .map(item -> new GithubRepository(
                        item.id(),
                        item.name(),
                        item.description(),
                        item.owner().login(),
                        item.language(),
                        // item.stargazersCount(),
                        // item.forksCount(),
                        // item.updatedAt()
                        item.stars(),
                        item.forks(),
                        item.lastUpdated()
                        ))
                .toList();

        return repository.saveAll(repositories);
    }

    @Transactional(readOnly = true)
    public List<GithubRepository> getAllRepositories() {
        return repository.findAll();
    }
}
