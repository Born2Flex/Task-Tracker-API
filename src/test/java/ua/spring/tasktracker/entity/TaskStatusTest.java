package ua.spring.tasktracker.entity;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskStatusTest {

    @ParameterizedTest
    @EnumSource(TaskStatus.class)
    void shouldReturnTrueForValidTransitions(TaskStatus currentStatus) {
        for (TaskStatus newStatus : currentStatus.getValidTransitions()) {
            assertTrue(TaskStatus.isValidTransition(currentStatus, newStatus));
        }
    }

    @ParameterizedTest
    @EnumSource(TaskStatus.class)
    void shouldReturnFalseForInvalidTransitions(TaskStatus currentStatus) {
        for (TaskStatus newStatus : TaskStatus.values()) {
            if (!currentStatus.getValidTransitions().contains(newStatus)) {
                assertFalse(TaskStatus.isValidTransition(currentStatus, newStatus));
            }
        }
    }
}