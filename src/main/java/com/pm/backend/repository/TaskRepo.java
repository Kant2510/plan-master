package com.pm.backend.repository;

import com.pm.backend.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Integer> {
    List<Task> findByPhase_Id(Integer phase_id);
}
