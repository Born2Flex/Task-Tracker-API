package ua.spring.tasktracker.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.spring.tasktracker.dto.task.*;
import ua.spring.tasktracker.entity.Task;
import ua.spring.tasktracker.entity.TaskStatus;
import ua.spring.tasktracker.repository.TaskRepository;
import ua.spring.tasktracker.utils.exceptions.InvalidTaskStatusTransitionException;
import ua.spring.tasktracker.utils.exceptions.TaskNotFoundException;
import ua.spring.tasktracker.utils.mapper.TaskMapper;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper mapper;

    public TaskDTO createTask(TaskCreationDTO task) {
        log.info("Creating new task");
        Task dbTask = mapper.toEntity(task);
        return mapper.toDTO(taskRepository.save(dbTask));
    }

    public TaskDTO updateTaskStatus(TaskStatusDTO task, Long id) {
        Task dbTask = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        log.info("Updating task with id {}", id);
        if (!isValidTransition(dbTask.getStatus(), task.getStatus())) {
            log.info("Invalid status transition. Task status: {}, new status: {}", dbTask.getStatus(), task.getStatus());
            throw new InvalidTaskStatusTransitionException("Can't change status from " + dbTask.getStatus()
                    + " to " + task.getStatus());
        }
        dbTask.setStatus(task.getStatus());
        return mapper.toDTO(taskRepository.save(dbTask));
    }

    public void deleteTask(Long id) {
        taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        log.info("Deleting task with id {}", id);
        taskRepository.deleteById(id);
    }

    public TaskDTO getTaskById(Long id) {
        log.info("Getting task by id {}", id);
        return mapper.toDTO(taskRepository.findById(id).orElseThrow(TaskNotFoundException::new));
    }

    public TaskPageDTO getAllTasks(Pageable pageable) {
        log.info("Getting all tasks");
        Page<Task> page = taskRepository.findAll(pageable);
        List<TaskDTO> tasks = mapper.toListDTO(page.getContent());
        return new TaskPageDTO(pageable.getPageNumber(), page.getTotalPages(), tasks);
    }

    public TaskPageShortDTO getAllTasksByUserId(Long userId, Pageable pageable) {
        log.info("Getting all tasks by user id {}", userId);
        Page<Task> page = taskRepository.findByUser_Id(userId, pageable);
        List<TaskShortDTO> tasks = mapper.toListShortDTO(page.getContent());
        return new TaskPageShortDTO(pageable.getPageNumber(), page.getTotalPages(), tasks);
    }

    private boolean isValidTransition(TaskStatus currentStatus, TaskStatus newStatus) {
        return switch (currentStatus) {
            case PLANNED:
                yield newStatus == TaskStatus.IN_PROGRESS || newStatus == TaskStatus.CANCELLED;
            case IN_PROGRESS:
                yield newStatus == TaskStatus.SIGNED || newStatus == TaskStatus.CANCELLED;
            case SIGNED:
                yield newStatus == TaskStatus.DONE || newStatus == TaskStatus.CANCELLED;
            default:
                yield false;
        };
    }
}
