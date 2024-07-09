package org.interview.empik.controller;

import org.interview.empik.dto.UserDto;
import org.interview.empik.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void returnUserAndStatusHttp200() throws Exception {
        var login = "test-login";
        var userDto = createDummyUserDto();
        when(userService.getUser(login)).thenReturn(userDto);

        mockMvc.perform(get(String.format("/v1/users/%s", login))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDto.getId()))
                .andExpect(jsonPath("$.login").value(userDto.getLogin()))
                .andExpect(jsonPath("$.name").value(userDto.getName()))
                .andExpect(jsonPath("$.avatarUrl").value(userDto.getAvatarUrl()))
                .andExpect(jsonPath("$.createdAt").value(userDto.getCreatedAt()))
                .andExpect(jsonPath("$.calculations").value(userDto.getCalculations()));
    }

    @Test
    public void ifLoginNotExistsReturnStatusHttp404() throws Exception {
        var login = "test-login";
        when(userService.getUser(login)).thenThrow(HttpClientErrorException.NotFound.class);

        mockMvc.perform(get(String.format("/v1/users/%s", login))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private UserDto createDummyUserDto() {
        return UserDto.builder()
                .id("test-id")
                .login("test-login")
                .name("test-name")
                .avatarUrl("test-avatar-url")
                .createdAt("test-created-at")
                .calculations("test-calculations")
                .build();
    }
}