package ua.spring.tasktracker.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.spring.tasktracker.dto.task.TaskCreationDTO;
import ua.spring.tasktracker.dto.task.TaskDTO;
import ua.spring.tasktracker.dto.task.TaskPageDTO;
import ua.spring.tasktracker.entity.Task;
import ua.spring.tasktracker.repository.TaskRepository;
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

    public TaskPageDTO getAllTasksByUserId(Long userId, Pageable pageable) {
        log.info("Getting all tasks by user id {}", userId);
        Page<Task> page = taskRepository.findByUser_Id(userId, pageable);
        List<TaskDTO> tasks = mapper.toListDTO(page.getContent());
        return new TaskPageDTO(pageable.getPageNumber(), page.getTotalPages(), tasks);
    }
}
