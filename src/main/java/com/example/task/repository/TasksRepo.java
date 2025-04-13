package com.example.task.repository;

import com.example.task.model.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TasksRepo extends JpaRepository<Tasks, String> {

    @Query("select t from Tasks t where t.user.userId =  :userId and t.deletedAt is null")
    List<Tasks> findByUserId(String userId);

    @Query("select t from Tasks t where t.user.userId =  :userId and t.stage.stageName != 'Completed' and t.deletedAt is null")
    List<Tasks> findOnlyIncompleteByUserId(String userId);

    @Query("select t from Tasks t where t.user.userId =  :userId and t.stage.stageName = 'Completed' and t.deletedAt is null")
    List<Tasks> findOnlyCompleteByUserId(String userId);

}
