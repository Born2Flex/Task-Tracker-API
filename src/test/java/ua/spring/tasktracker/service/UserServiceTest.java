package ua.spring.tasktracker.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.spring.tasktracker.dto.user.UserCreationDTO;
import ua.spring.tasktracker.dto.user.UserDTO;
import ua.spring.tasktracker.dto.user.UserUpdateDTO;
import ua.spring.tasktracker.entity.User;
import ua.spring.tasktracker.repository.UserRepository;
import ua.spring.tasktracker.utils.exceptions.EmailDuplicateException;
import ua.spring.tasktracker.utils.exceptions.UserNotFoundException;
import ua.spring.tasktracker.utils.mapper.UserMapper;

import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private Principal principal;

    @InjectMocks
    private UserService userService;

    private UserCreationDTO userCreationDTO;
    private UserUpdateDTO userUpdateDTO;
    private User user;

    @BeforeEach
    void setUp() {
        userCreationDTO = new UserCreationDTO();
        userCreationDTO.setEmail("test@test.com");

        userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setEmail("test@test.com");


        user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");
    }

    @Test
    void shouldCreateUser() {
        when(userRepository.existsByEmailIgnoreCase(any(String.class))).thenReturn(false);
        when(userMapper.toEntity(any(UserCreationDTO.class), any())).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toDTO(any(User.class))).thenReturn(new UserDTO());

        userService.createUser(userCreationDTO);

        verify(userRepository, times(1)).existsByEmailIgnoreCase(any(String.class));
        verify(userMapper, times(1)).toEntity(any(UserCreationDTO.class), any());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldThrowEmailDuplicateExceptionWhenEmailAlreadyInUse() {
        when(userRepository.existsByEmailIgnoreCase(any(String.class))).thenReturn(true);

        assertThrows(EmailDuplicateException.class, () -> userService.createUser(userCreationDTO));
    }

    @Test
    void shouldGetUserById() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(userMapper.toDTO(any(User.class))).thenReturn(new UserDTO());

        userService.getUserById(1L);

        verify(userRepository, times(1)).findById(any(Long.class));
        verify(userMapper, times(1)).toDTO(any(User.class));
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenUserNotFound() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }

//    @Test
//    void shouldUpdateUser() {
//        try (MockedStatic<UserService> utilities = Mockito.mockStatic(UserService.class)) {
//            utilities.when(() -> UserService.validateAccess(1L, principal)).thenAnswer(invocation -> null);
//        }
//
//        when(userRepository.existsByEmailIgnoreCase(any(String.class))).thenReturn(false);
//        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
//        when(userRepository.save(any(User.class))).thenReturn(user);
//        when(userMapper.toDTO(any(User.class))).thenReturn(new UserDTO());
//
//        userService.updateUser(userUpdateDTO, 1L, principal);
//
//        verify(userRepository, times(1)).existsByEmailIgnoreCase(any(String.class));
//        verify(userRepository, times(1)).findById(any(Long.class));
//        verify(userRepository, times(1)).save(any(User.class));
//    }

//    @Test
//    void shouldDeleteUser() {
//        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
//
//        userService.deleteUser(1L, principal);
//
//        verify(userRepository, times(1)).findById(any(Long.class));
//        verify(userRepository, times(1)).deleteById(any(Long.class));
//    }
}