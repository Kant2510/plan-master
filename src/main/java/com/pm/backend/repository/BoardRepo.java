package com.pm.backend.repository;

import com.pm.backend.model.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pm.backend.model.Board;

@Repository
public interface BoardRepo extends JpaRepository<Board, Integer> {
    Board findBySprint(Sprint sprint);
}
