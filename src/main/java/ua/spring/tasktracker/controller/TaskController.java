package ua.spring.tasktracker.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ua.spring.tasktracker.dto.task.TaskCreationDTO;
import ua.spring.tasktracker.dto.task.TaskDTO;
import ua.spring.tasktracker.dto.task.TaskPageDTO;
import ua.spring.tasktracker.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public TaskDTO createTask(@RequestBody @Valid TaskCreationDTO task) {
        return taskService.createTask(task);
    }

    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @GetMapping
    public TaskPageDTO getAllTasks(@PageableDefault(size = 5, sort = "id",
            direction = Sort.Direction.ASC) Pageable pageable) {
        return taskService.getAllTasks(pageable);
    }
}
