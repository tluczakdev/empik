package org.interview.empik.service;

import org.interview.empik.dto.UserDto;

public interface UserService {

    UserDto getUser(String login);
}
