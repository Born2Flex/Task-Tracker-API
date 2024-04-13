package ua.spring.tasktracker.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.spring.tasktracker.dto.task.TaskCreationDTO;
import ua.spring.tasktracker.dto.task.TaskDTO;
import ua.spring.tasktracker.entity.Task;
import ua.spring.tasktracker.repository.TaskRepository;
import ua.spring.tasktracker.utils.mapper.TaskMapper;

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
}
