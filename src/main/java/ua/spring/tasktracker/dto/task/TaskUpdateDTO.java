package ua.spring.tasktracker.dto.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskUpdateDTO {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotNull
    private LocalDate date;
    @NotNull
    private Long userId;
}
