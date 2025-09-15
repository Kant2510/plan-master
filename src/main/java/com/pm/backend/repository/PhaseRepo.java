package com.pm.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pm.backend.model.Phase;

import java.util.List;

@Repository
public interface PhaseRepo extends JpaRepository<Phase, Integer> {
    // Define any additional query methods if needed
    List<Phase> findByBoard_Id(Integer boardId);
}
