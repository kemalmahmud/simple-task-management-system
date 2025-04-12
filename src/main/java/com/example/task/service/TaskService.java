package com.example.task.service;

import com.example.task.dto.request.*;
import com.example.task.dto.response.*;
import com.example.task.model.Tasks;
import com.example.task.repository.TasksRepo;
import com.example.task.repository.UsersRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class TaskService {

    @Autowired
    TasksRepo tasksRepo;
    @Autowired
    UsersRepo usersRepo;

    // add task
    public ResponseEntity<BaseResponse> addTask(AddTaskRequest request) {
        BaseResponse response = new BaseResponse();
        try {
            var user = usersRepo.findById(request.getUserId()).orElse(null);
            if(user == null) throw new Exception("User nor found");

            var task = Tasks.builder()
                    .taskName(request.getTaskName())
                    .taskDetail(request.getTaskDetail())
                    .user(user)
                    .createdAt(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(response);
        }
        catch(Exception e) {
            log.error("Error in addTask, message : " + e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Something wrong happened");
            response.setData(null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    // edit task detail
    public ResponseEntity<BaseResponse> editTask(EditTaskRequest request) {
        BaseResponse response = new BaseResponse();
        try {
            return ResponseEntity.ok(response);
        }
        catch(Exception e) {
            log.error("Error in editTask, message : " + e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Something wrong happened");
            response.setData(null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    // remove task
    public ResponseEntity<BaseResponse> removeTask(RemoveTaskRequest request) {
        BaseResponse response = new BaseResponse();
        try {
            return ResponseEntity.ok(response);
        }
        catch(Exception e) {
            log.error("Error in removeTask, message : " + e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Something wrong happened");
            response.setData(null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    // update task stage
    public ResponseEntity<BaseResponse> updateTask(UpdateTaskRequest request) {
        BaseResponse response = new BaseResponse();
        try {
            return ResponseEntity.ok(response);
        }
        catch(Exception e) {
            log.error("Error in updateTask, message : " + e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Something wrong happened");
            response.setData(null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    // complete task
    public ResponseEntity<BaseResponse> completeTask(CompleteTaskRequest request) {
        BaseResponse response = new BaseResponse();
        try {
            return ResponseEntity.ok(response);
        }
        catch(Exception e) {
            log.error("Error in completeTask, message : " + e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Something wrong happened");
            response.setData(null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

}
