package ua.spring.tasktracker.utils.mapper;

import org.mapstruct.Mapper;
import ua.spring.tasktracker.dto.user.UserCreationDTO;
import ua.spring.tasktracker.dto.user.UserDTO;
import ua.spring.tasktracker.entity.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserCreationDTO userDTO);

    UserDTO toDTO(User user);

    List<UserDTO> toListDTO(List<User> users);
}
