package ua.spring.tasktracker.dto.task;

import lombok.Data;
import ua.spring.tasktracker.dto.user.UserDTO;
import ua.spring.tasktracker.entity.TaskStatus;

@Data
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private String date;
    private TaskStatus status;
    private UserDTO user;
}
