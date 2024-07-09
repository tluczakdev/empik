package org.interview.empik.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.interview.empik.dto.GitHubUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class GitHubUserServiceImplTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GitHubUserService gitHubUserService;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void init() {
        this.mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void shouldReturnGitHubUser() throws JsonProcessingException {
        // given
        GitHubUserDto gitHubUserDto = createGitHubUserDto();

        mockServer.expect(requestTo(GitHubUserServiceImpl.API_URL + gitHubUserDto.getLogin()))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(gitHubUserDto)));

        // when
        GitHubUserDto result = gitHubUserService.getGitHubUser(gitHubUserDto.getLogin());

        // then
        assertEquals(result.getId(), gitHubUserDto.getId());
        assertEquals(result.getLogin(), gitHubUserDto.getLogin());
        assertEquals(result.getName(), gitHubUserDto.getName());
        assertEquals(result.getAvatarUrl(), gitHubUserDto.getAvatarUrl());
        assertEquals(result.getCreatedAt(), gitHubUserDto.getCreatedAt());
        assertEquals(result.getFollowers(), gitHubUserDto.getFollowers());
        assertEquals(result.getPublicRepos(), gitHubUserDto.getPublicRepos());
    }

    private GitHubUserDto createGitHubUserDto() {
        return GitHubUserDto.builder()
                .id("test-id")
                .login("test-login")
                .name("test-name")
                .avatarUrl("test-avatar-url")
                .createdAt("test-created-at")
                .followers(1000)
                .publicRepos(2)
                .build();
    }
}