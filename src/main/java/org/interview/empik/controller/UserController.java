package org.interview.empik.controller;

import org.interview.empik.controller.model.User;
import org.interview.empik.dto.UserDto;
import org.interview.empik.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(path = "/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable("login") String login) {
        return convertUserDtoToUser(userService.getUser(login));
    }

    private User convertUserDtoToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .login(userDto.getLogin())
                .name(userDto.getName())
                .avatarUrl(userDto.getAvatarUrl())
                .createdAt(userDto.getCreatedAt())
                .calculations(userDto.getCalculations())
                .build();
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<ErrorResponse> handleNotFound(Exception ex) {
        ErrorResponse error = ErrorResponse.create(ex, NOT_FOUND, "User is not found");
        return new ResponseEntity<>(error, NOT_FOUND);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = ErrorResponse.create(ex, INTERNAL_SERVER_ERROR, "Something is wrong");
        return new ResponseEntity<>(error, INTERNAL_SERVER_ERROR);
    }
}
