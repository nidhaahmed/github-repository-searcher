package com.freightfox.githubsearcher.controller;

import com.freightfox.githubsearcher.entity.GithubRepository;
import com.freightfox.githubsearcher.service.GithubRepositoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/repositories")
public class GithubRepositoryController {

    private final GithubRepositoryService service;

    public GithubRepositoryController(GithubRepositoryService service) {
        this.service = service;
    }

    @GetMapping("/search")
    public List<GithubRepository> searchRepositories(
            @RequestParam String query,
            @RequestParam(required = false) String language,
            @RequestParam(defaultValue = "stars") String sort) {

        return service.searchRepositories(query, language, sort);
    }

    @GetMapping
    public List<GithubRepository> getAllRepositories() {
        return service.getAllRepositories();
    }
}