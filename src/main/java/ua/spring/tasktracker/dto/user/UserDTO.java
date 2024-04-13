package ua.spring.tasktracker.dto.user;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String role;
}
