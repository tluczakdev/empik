package org.interview.empik.service;

import org.interview.empik.dto.GitHubUserDto;
import org.interview.empik.dto.UserDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserServiceImpl implements UserService {

    private final GitHubUserService gitHubUserService;

    private final VisitCounterService visitCounterService;

    private final CalculationStrategy<BigDecimal, Integer, Integer> calculationStrategy;

    public UserServiceImpl(
            GitHubUserService gitHubUserService,
            VisitCounterService visitCounterService,
            CalculationStrategy<BigDecimal, Integer, Integer> calculationStrategy) {

        this.gitHubUserService = gitHubUserService;
        this.visitCounterService = visitCounterService;
        this.calculationStrategy = calculationStrategy;
    }

    /**
     * Retrieves user data and counts the visits
     *
     * @param login
     * @return UserDto
     */
    @Override
    public UserDto getUser(String login) {

        visitCounterService.visitedBy(login);

        GitHubUserDto gitHubUserDto = gitHubUserService.getGitHubUser(login);

        return convertGitHubUserDtoToUserDto(gitHubUserDto);
    }

    private UserDto convertGitHubUserDtoToUserDto(GitHubUserDto externalUserDto) {
        return UserDto.builder()
                .id(externalUserDto.getId())
                .login(externalUserDto.getLogin())
                .name(externalUserDto.getName())
                .avatarUrl(externalUserDto.getAvatarUrl())
                .createdAt(externalUserDto.getCreatedAt())
                .calculations(calculationStrategy.calculate(externalUserDto.getFollowers(), externalUserDto.getPublicRepos()).toString())
                .build();
    }
}
