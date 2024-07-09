package org.interview.empik.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GitHubUserDto {
    public String id;
    public String login;
    public String name;
    public String avatarUrl;
    public String createdAt;
    public Integer followers;
    public Integer publicRepos;
}
