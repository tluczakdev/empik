package org.interview.empik.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.interview.empik.common.exception.InternalException;
import org.interview.empik.dto.GitHubUserDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static java.net.URI.create;
import static java.util.Optional.ofNullable;

@Service
public class GitHubUserServiceImpl implements GitHubUserService {

    public static final String API_URL = "https://api.github.com/users/";

    private final ObjectMapper mapper;

    private final RestTemplate restTemplate;

    public GitHubUserServiceImpl(ObjectMapper mapper, RestTemplate restTemplate) {
        this.mapper = mapper;
        this.restTemplate = restTemplate;
    }

    @Override
    public GitHubUserDto getGitHubUser(String login) {

        String responseBody = ofNullable(retrieveGitHubUserData(login))
                .orElseThrow(InternalException::new);

        return convertResponseBodyToGitHubUserDto(responseBody);
    }

    private String retrieveGitHubUserData(String login) {
        return restTemplate
                .getForEntity(create(API_URL + login), String.class)
                .getBody();
    }

    private GitHubUserDto convertResponseBodyToGitHubUserDto(String responseBody) {
        try {
            return mapper.readValue(responseBody, GitHubUserDto.class);
        } catch (JsonProcessingException e) {
            throw new InternalException(e);
        }
    }

}
