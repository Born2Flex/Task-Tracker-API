package ua.spring.tasktracker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.spring.tasktracker.dto.task.TaskPageShortDTO;
import ua.spring.tasktracker.dto.user.UserCreationDTO;
import ua.spring.tasktracker.dto.user.UserDTO;
import ua.spring.tasktracker.dto.user.UserPageDTO;
import ua.spring.tasktracker.dto.user.UserUpdateDTO;
import ua.spring.tasktracker.service.TaskService;
import ua.spring.tasktracker.service.UserService;
import ua.spring.tasktracker.utils.exceptionhandler.ApiError;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add user", description = "Add a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Added new user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserCreationDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid user data",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))}),
    })
    public UserDTO createUser(@RequestBody @Valid UserCreationDTO userCreationDTO) {
        return userService.createUser(userCreationDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Update an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid user data",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))})
    })
    public UserDTO updateUser(@RequestBody @Valid UserUpdateDTO userDTO, @PathVariable Long id) {
        return userService.updateUser(userDTO, id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by id", description = "Get user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))}),
    })
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete user", description = "Delete a user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found users",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))}),
    })
    public UserPageDTO getAllUsers(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

    @GetMapping("/{id}/tasks")
    @Operation(summary = "Get all tasks of user", description = "Get all tasks of user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found tasks",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))}),
    })
    public TaskPageShortDTO getAllTasksOfUser(@PathVariable Long id, @PageableDefault(size = 5, sort = "id",
            direction = Sort.Direction.ASC) Pageable pageable) {
        return taskService.getAllTasksByUserId(id, pageable);
    }
}
