package ua.spring.tasktracker.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.spring.tasktracker.dto.user.UserCreationDTO;
import ua.spring.tasktracker.dto.user.UserDTO;
import ua.spring.tasktracker.dto.user.UserPageDTO;
import ua.spring.tasktracker.dto.user.UserUpdateDTO;
import ua.spring.tasktracker.entity.User;
import ua.spring.tasktracker.repository.UserRepository;
import ua.spring.tasktracker.utils.exceptions.EmailDuplicateException;
import ua.spring.tasktracker.utils.exceptions.UserNotFoundException;
import ua.spring.tasktracker.utils.mapper.UserMapper;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDTO createUser(UserCreationDTO user) {
        log.info("Creating new user");
        if (emailAlreadyInUse(user.getEmail())) {
            log.info("Email {} is already in use", user.getEmail());
            throw new EmailDuplicateException();
        }
        User dbUser = userMapper.toEntity(user);
        return userMapper.toDTO(userRepository.save(dbUser));
    }

    public UserDTO getUserById(Long id) {
        log.info("Getting user by id {}", id);
        return userMapper.toDTO(userRepository.findById(id).orElseThrow(UserNotFoundException::new));
    }

    public UserDTO updateUser(UserUpdateDTO user, Long id) {
        if (emailAlreadyInUse(user.getEmail())) {
            log.error("Email {} is already in use", user.getEmail());
            throw new EmailDuplicateException();
        }
        User dbUser = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        dbUser.setName(user.getName());
        dbUser.setSurname(user.getSurname());
        dbUser.setEmail(user.getEmail());
        log.info("Updating user with id {}", id);
        return userMapper.toDTO(userRepository.save(dbUser));
    }

    public void deleteUser(Long id) {
        userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        log.info("Deleting user with id {}", id);
        userRepository.deleteById(id);
    }

    public UserPageDTO getAllUsers(Pageable pageable) {
        log.info("Getting all users");
        Page<User> page = userRepository.findAll(pageable);
        List<UserDTO> users = userMapper.toListDTO(page.getContent());
        return new UserPageDTO(page.getNumber(), page.getTotalPages(), users);
    }

    private boolean emailAlreadyInUse(String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }
}
