package com.pm.backend.repository;

import com.pm.backend.model.TaskAssignee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskAssigneeRepo extends JpaRepository<TaskAssignee, Integer> {
}
