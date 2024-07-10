package org.interview.empik.controller;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Pattern;
import org.interview.empik.controller.model.User;
import org.interview.empik.dto.UserDto;
import org.interview.empik.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import static org.springframework.http.HttpStatus.*;

@Validated
@RestController
@RequestMapping(path = "/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     *
     * @param login
     * - Usernames can only contain alphanumeric characters (a-z, 0-9) and hyphens (-)
     * - Underscores (_) and dots (.) are not allowed.
     * - The maximum length for a username is 39 characters.
     * - Usernames cannot begin or end with a hyphen.
     * - Multiple consecutive hyphens are not allowed.
     * - Usernames are case-insensitive
     *
     * @return basic user information
     */

    @GetMapping(path = "/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable ("login") @Pattern(regexp = "^(?i)(?!.*--)[a-z0-9](?:[a-z0-9-]{0,37}[a-z0-9])?$") String login) {
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

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(Exception ex) {
        ErrorResponse error = ErrorResponse.create(ex, BAD_REQUEST, "Wrong login");
        return new ResponseEntity<>(error, BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = ErrorResponse.create(ex, INTERNAL_SERVER_ERROR, "Something is wrong");
        return new ResponseEntity<>(error, INTERNAL_SERVER_ERROR);
    }
}
