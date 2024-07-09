package org.interview.empik.service;

import org.interview.empik.dto.GitHubUserDto;

public interface GitHubUserService {

    GitHubUserDto getGitHubUser(String login);
}
