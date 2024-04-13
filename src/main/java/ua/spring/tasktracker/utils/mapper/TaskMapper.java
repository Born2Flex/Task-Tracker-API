package ua.spring.tasktracker.utils.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import ua.spring.tasktracker.dto.task.TaskCreationDTO;
import ua.spring.tasktracker.dto.task.TaskDTO;
import ua.spring.tasktracker.entity.Task;
import ua.spring.tasktracker.repository.UserRepository;
import ua.spring.tasktracker.utils.exceptions.UserNotFoundException;

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

}
