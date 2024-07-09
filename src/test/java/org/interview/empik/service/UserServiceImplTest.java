package org.interview.empik.service;

import org.interview.empik.dto.GitHubUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private GitHubUserService gitHubUserService;

    private VisitCounterService visitCounterService;

    private UserService userService;

    @BeforeEach
    public void init() {
        this.gitHubUserService = mock(GitHubUserService.class);
        this.visitCounterService = mock(VisitCounterService.class);
        this.userService = new UserServiceImpl(gitHubUserService, visitCounterService);
    }

    @Test
    public void ifLoginIsCorrectReturnUserDtoAndCountsVisits() {
        // given
        String login = "test-login";
        var externalUserDto = createGitHubUserDto(1000, 20);
        when(gitHubUserService.getGitHubUser(login)).thenReturn(externalUserDto);

        // when
        var userDto = userService.getUser(login);

        // then
        verify(visitCounterService).visitedBy(login);

        assertEquals(userDto.getId(), externalUserDto.getId());
        assertEquals(userDto.getLogin(), externalUserDto.getLogin());
        assertEquals(userDto.getName(), externalUserDto.getName());
        assertEquals(userDto.getAvatarUrl(), externalUserDto.getAvatarUrl());
        assertEquals(userDto.getCreatedAt(), externalUserDto.getCreatedAt());
        assertEquals(userDto.getCalculations(), "0");
    }

    /**
     * formula
     * 6 / followers * (2 + publicRepos)
     */
    @ParameterizedTest
    @MethodSource("calculations")
    public void calculationsShouldBeCalculateBasedOnFormula(int followers, int publicRepos, String calculations) {
        // given
        String login = "test-login";
        var externalUserDto = createGitHubUserDto(followers, publicRepos);
        when(gitHubUserService.getGitHubUser(login)).thenReturn(externalUserDto);

        // when
        var userDto = userService.getUser(login);

        // then
        assertEquals(userDto.getCalculations(), calculations);
    }

    private GitHubUserDto createGitHubUserDto(int followers, int publicRepos) {
        return GitHubUserDto.builder()
                .id("test-id")
                .login("test-login")
                .name("test-name")
                .avatarUrl("test-avatar-url")
                .createdAt("test-created-at")
                .followers(followers)
                .publicRepos(publicRepos)
                .build();
    }

    static Stream<Arguments> calculations() {
        return Stream.of(
                Arguments.of(1, 1, "2"),
                Arguments.of(0, 1, "0"),
                Arguments.of(1, 0, "3"),
                Arguments.of(1000, 0, "0")
        );
    }
}