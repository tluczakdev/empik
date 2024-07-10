package org.interview.empik.service;

import org.interview.empik.dto.GitHubUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private GitHubUserService gitHubUserService;

    @Mock
    private VisitCounterService visitCounterService;

    @Mock
    private CalculationStrategy<BigDecimal, Integer, Integer> calculationStrategy;

    private UserService userService;

    @BeforeEach
    public void init() {
        this.userService = new UserServiceImpl(gitHubUserService, visitCounterService, calculationStrategy);
    }

    @Test
    public void returnUserDtoAndCountsVisits() {
        // given
        var login = "test-login";
        var externalUserDto = createGitHubUserDto();
        var calculations = BigDecimal.ZERO;

        when(gitHubUserService.getGitHubUser(login)).thenReturn(externalUserDto);
        when(calculationStrategy.calculate(anyInt(),anyInt())).thenReturn(calculations);

        // when
        var userDto = userService.getUser(login);

        // then
        verify(visitCounterService).visitedBy(login);

        assertEquals(userDto.getId(), externalUserDto.getId());
        assertEquals(userDto.getLogin(), externalUserDto.getLogin());
        assertEquals(userDto.getName(), externalUserDto.getName());
        assertEquals(userDto.getAvatarUrl(), externalUserDto.getAvatarUrl());
        assertEquals(userDto.getCreatedAt(), externalUserDto.getCreatedAt());
        assertEquals(userDto.getCalculations(), calculations.toString());
    }

    private GitHubUserDto createGitHubUserDto() {
        return GitHubUserDto.builder()
                .id("test-id")
                .login("test-login")
                .name("test-name")
                .avatarUrl("test-avatar-url")
                .createdAt("test-created-at")
                .followers(1000)
                .publicRepos(20)
                .build();
    }
}