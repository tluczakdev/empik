package org.interview.empik.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GitHubUserDto {
    String id;
    String login;
    String name;
    String avatarUrl;
    String createdAt;
    Integer followers;
    Integer publicRepos;
}
