package ua.spring.tasktracker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.spring.tasktracker.dto.task.*;
import ua.spring.tasktracker.service.TaskService;
import ua.spring.tasktracker.utils.exceptionhandler.ApiError;

import java.security.Principal;
import java.util.Locale;

@RestController
@RequestMapping("/api/tasks")
@SecurityRequirement(name = "basicAuth")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final MessageSource messageSource;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Create task", description = "Create a new task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskCreationDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid task data",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))}),
    })
    public TaskDTO createTask(@RequestBody @Valid TaskCreationDTO task, Principal principal
            , @RequestHeader(name = "lang", required = false) Locale locale) {
        String greetingMessage = messageSource.getMessage("greeting.message", null, locale);
        return taskService.createTask(task, principal);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Update task", description = "Update an existing task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid task data",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))})
    })
    public TaskDTO updateTask(@RequestBody @Valid TaskUpdateDTO task, @PathVariable Long id, Principal principal) {
        return taskService.updateTask(task, id, principal);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Update task status", description = "Update the status of an existing task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task status updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid task status data",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))})
    })
    public TaskDTO updateStatus(@RequestBody @Valid TaskStatusDTO task, @PathVariable Long id, Principal principal) {
        return taskService.updateTaskStatus(task, id, principal);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete task", description = "Delete task by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))}),
    })
    public void deleteTask(@PathVariable Long id, Principal principal) {
        taskService.deleteTask(id, principal);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @Operation(summary = "Get all tasks", description = "Get all tasks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskPageDTO.class))}),
    })
    public TaskPageDTO getAllTasks(@PageableDefault(size = 5, sort = "id",
            direction = Sort.Direction.ASC) Pageable pageable) {
        return taskService.getAllTasks(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @Operation(summary = "Get task by id", description = "Get task by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))}),
    })
    public TaskDTO getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

}
