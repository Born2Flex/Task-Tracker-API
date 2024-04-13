package ua.spring.tasktracker.utils.exceptions;

public class EmailDuplicateException extends RuntimeException {
    public EmailDuplicateException() {
        super("User with such email already exists");
    }
}
