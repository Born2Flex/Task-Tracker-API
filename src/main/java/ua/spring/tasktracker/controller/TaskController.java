package ua.spring.tasktracker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.spring.tasktracker.dto.task.TaskCreationDTO;
import ua.spring.tasktracker.dto.task.TaskDTO;
import ua.spring.tasktracker.dto.task.TaskPageDTO;
import ua.spring.tasktracker.dto.task.TaskStatusDTO;
import ua.spring.tasktracker.service.TaskService;
import ua.spring.tasktracker.utils.exceptionhandler.ApiError;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create task", description = "Create a new task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created successfully",
                    content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = TaskCreationDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid task data",
                    content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ApiError.class))}),
    })
    public TaskDTO createTask(@RequestBody @Valid TaskCreationDTO task) {
        return taskService.createTask(task);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update task status", description = "Update the status of an existing task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task status updated successfully",
                    content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = TaskDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ApiError.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid task status data",
                    content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ApiError.class))})
    })
    public TaskDTO updateStatus(@RequestBody @Valid TaskStatusDTO task, @PathVariable Long id) {
        return taskService.updateTaskStatus(task, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete task", description = "Delete task by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ApiError.class))}),
    })
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @GetMapping
    @Operation(summary = "Get all tasks", description = "Get all tasks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks found",
                    content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = TaskPageDTO.class))}),
    })
    public TaskPageDTO getAllTasks(@PageableDefault(size = 5, sort = "id",
            direction = Sort.Direction.ASC) Pageable pageable) {
        return taskService.getAllTasks(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get task by id", description = "Get task by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task found",
                    content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = TaskDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ApiError.class))}),
    })
    public TaskDTO getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

}
