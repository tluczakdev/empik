package org.interview.empik.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {
    String id;
    String login;
    String name;
    String avatarUrl;
    String createdAt;
    String calculations;
}
