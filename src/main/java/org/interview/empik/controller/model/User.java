package org.interview.empik.controller.model;

import lombok.Builder;

@Builder
public class User {
    public String id;
    public String login;
    public String name;
    public String avatarUrl;
    public String createdAt;
    public String calculations;
}
