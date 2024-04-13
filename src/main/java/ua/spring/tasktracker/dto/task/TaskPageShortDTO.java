package ua.spring.tasktracker.dto.task;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TaskPageShortDTO {
    private Integer currPage;
    private Integer numOfPages;
    private List<TaskShortDTO> tasks;
}
