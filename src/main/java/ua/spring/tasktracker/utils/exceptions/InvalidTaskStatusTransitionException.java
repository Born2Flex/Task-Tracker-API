package ua.spring.tasktracker.utils.exceptions;

public class InvalidTaskStatusTransitionException extends RuntimeException {
    public InvalidTaskStatusTransitionException(String message) {
        super(message);
    }
}
