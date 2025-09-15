package com.pm.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pm.backend.model.Sprint;

import java.util.List;

@Repository
public interface SprintRepo extends JpaRepository<Sprint, Integer> {
    List<Sprint> findByProject_Id(Integer project_id);
}
