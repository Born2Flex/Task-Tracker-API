package ua.spring.tasktracker.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserPageDTO {
    private Integer currPage;
    private Integer numOfPages;
    private List<UserDTO> users;
}
