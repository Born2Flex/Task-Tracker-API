package ua.spring.tasktracker.utils.exceptions;

public class TaskStateTransitionException extends RuntimeException {
    public TaskStateTransitionException(String message) {
        super(message);
    }
}
