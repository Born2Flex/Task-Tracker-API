package ua.spring.tasktracker.utils.exceptionhandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.spring.tasktracker.utils.exceptions.EmailDuplicateException;
import ua.spring.tasktracker.utils.exceptions.TaskNotFoundException;
import ua.spring.tasktracker.utils.exceptions.TaskStateTransitionException;
import ua.spring.tasktracker.utils.exceptions.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalHandler {
    private final MessageSource messageSource;

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
        log.info("Not found: {}", e.getMessage());
        ApiError response = new ApiError(LocalDateTime.now(), HttpStatus.NOT_FOUND, e.getMessage(), 2, List.of(e.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailDuplicateException.class)
    public ResponseEntity<ApiError> handleEmailDuplicateException(Exception e) {
        ApiError response = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, e.getMessage(), 3, List.of(e.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TaskStateTransitionException.class)
    public ResponseEntity<ApiError> handleTaskStateTransitionException(TaskStateTransitionException e) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage(e.getMessage(), null, locale);
        log.info("Task state transition error: {}", message);
        ApiError response = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, message, 4, List.of(message));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException e) {
        log.info("Access denied: {}", e.getMessage());
        ApiError response = new ApiError(LocalDateTime.now(), HttpStatus.FORBIDDEN, e.getMessage(), 4, List.of(e.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception e) {
        log.warn("Unexpected error: {}", e.getMessage());
        e.printStackTrace();
        ApiError response = new ApiError(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), 4, List.of(e.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
