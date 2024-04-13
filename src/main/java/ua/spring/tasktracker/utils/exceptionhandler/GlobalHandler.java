package ua.spring.tasktracker.utils.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.spring.tasktracker.utils.exceptions.EmailDuplicateException;
import ua.spring.tasktracker.utils.exceptions.TaskNotFoundException;
import ua.spring.tasktracker.utils.exceptions.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        log.info("Validation on controller failed: {}", errors);
        ApiError response = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Validation failed", 1, errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UserNotFoundException.class, TaskNotFoundException.class})
    public ResponseEntity<ApiError> handleNoSuchEntityException(Exception e) {
        log.info("User not found: {}", e.getMessage());
        ApiError response = new ApiError(LocalDateTime.now(), HttpStatus.NOT_FOUND, e.getMessage(), 2, List.of(e.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailDuplicateException.class)
    public ResponseEntity<ApiError> handleEmailDuplicateException(Exception e) {
        ApiError response = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, e.getMessage(), 3, List.of(e.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception e) {
        log.warn("Unexpected error: {}", e.getMessage());
        ApiError response = new ApiError(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), 4, List.of(e.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
