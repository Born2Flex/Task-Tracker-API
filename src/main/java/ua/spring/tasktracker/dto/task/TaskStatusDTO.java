package ua.spring.tasktracker.dto.task;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ua.spring.tasktracker.entity.TaskStatus;

@Data
public class TaskStatusDTO {
    @NotNull
    private Long id;
    @NotNull
    private TaskStatus status;
}
