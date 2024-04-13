package ua.spring.tasktracker.dto.task;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TaskPageDTO {
    private Integer currPage;
    private Integer numOfPages;
    private List<TaskDTO> tasks;
}
