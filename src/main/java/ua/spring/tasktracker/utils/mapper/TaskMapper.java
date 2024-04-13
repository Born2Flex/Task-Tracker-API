package ua.spring.tasktracker.utils.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import ua.spring.tasktracker.dto.task.TaskCreationDTO;
import ua.spring.tasktracker.dto.task.TaskDTO;
import ua.spring.tasktracker.dto.task.TaskShortDTO;
import ua.spring.tasktracker.entity.Task;
import ua.spring.tasktracker.repository.UserRepository;
import ua.spring.tasktracker.utils.exceptions.UserNotFoundException;

import java.util.List;

@Mapper(componentModel = "spring", imports = UserNotFoundException.class)
public abstract class TaskMapper {
    @Autowired
    protected UserRepository userRepository;

    @Mappings({
            @Mapping(target = "user", expression = "java(userRepository.findById(taskDTO.getUserId()).orElseThrow(UserNotFoundException::new))"),
            @Mapping(target = "status", expression = "java(ua.spring.tasktracker.entity.TaskStatus.PLANNED)")
    })
    public abstract Task toEntity(TaskCreationDTO taskDTO);
    public abstract TaskDTO toDTO(Task task);

    public abstract TaskShortDTO toShortDTO(Task task);
    public abstract List<TaskDTO> toListDTO(List<Task> tasks);

    public abstract List<TaskShortDTO> toListShortDTO(List<Task> tasks);
}
