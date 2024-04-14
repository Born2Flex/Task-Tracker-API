package ua.spring.tasktracker.entity;

import java.util.EnumSet;
import java.util.Set;

public enum TaskStatus {
    PLANNED,
    IN_PROGRESS,
    SIGNED,
    DONE,
    CANCELLED;

    private Set<TaskStatus> validTransitions;

    static {
        PLANNED.validTransitions = EnumSet.of(IN_PROGRESS, SIGNED, DONE, CANCELLED);
        IN_PROGRESS.validTransitions = EnumSet.of(SIGNED, DONE, CANCELLED);
        SIGNED.validTransitions = EnumSet.of(DONE, CANCELLED);
        DONE.validTransitions = EnumSet.noneOf(TaskStatus.class);
        CANCELLED.validTransitions = EnumSet.noneOf(TaskStatus.class);
    }

    public Set<TaskStatus> getValidTransitions() {
        return validTransitions;
    }

    public static boolean isValidTransition(TaskStatus currentStatus, TaskStatus newStatus) {
        return currentStatus.validTransitions.contains(newStatus);
    }
}