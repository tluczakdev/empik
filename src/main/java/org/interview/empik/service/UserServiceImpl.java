package org.interview.empik.service;

import org.interview.empik.dto.GitHubUserDto;
import org.interview.empik.dto.UserDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.BigDecimal.valueOf;

@Service
public class UserServiceImpl implements UserService {

    private final GitHubUserService gitHubUserService;

    private final VisitCounterService visitCounterService;

    public UserServiceImpl(GitHubUserService gitHubUserService, VisitCounterService visitCounterService) {
        this.gitHubUserService = gitHubUserService;
        this.visitCounterService = visitCounterService;
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
                .id(externalUserDto.id)
                .login(externalUserDto.login)
                .name(externalUserDto.name)
                .avatarUrl(externalUserDto.avatarUrl)
                .createdAt(externalUserDto.createdAt)
                .calculations(calculateCalculations(externalUserDto.followers, externalUserDto.publicRepos).toString())
                .build();
    }

    /**
     * calculate 'calculations' based on the formula
     * 6 / followers * (2 + publicRepos)
     *
     * @param followers
     * @param publicRepos
     * @return calculations
     */
    private BigDecimal calculateCalculations(Integer followers, Integer publicRepos) {
        BigDecimal numerator = valueOf(6);
        BigDecimal denominator = valueOf(followers).multiply(valueOf(2).add(valueOf(publicRepos)));
        return denominator.equals(BigDecimal.ZERO)
                ? BigDecimal.ZERO
                : numerator
                .divide(denominator, RoundingMode.HALF_UP);
    }
}
