package ua.spring.tasktracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.spring.tasktracker.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}