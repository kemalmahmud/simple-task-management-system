package com.example.task.repository;

import com.example.task.model.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StageRepo extends JpaRepository<Stage, Integer> {

    Optional<Stage> findByStageName(String stageName);
}
