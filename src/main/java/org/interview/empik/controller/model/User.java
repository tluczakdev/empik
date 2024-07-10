package org.interview.empik.controller.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
    String id;
    String login;
    String name;
    String avatarUrl;
    String createdAt;
    String calculations;
}
