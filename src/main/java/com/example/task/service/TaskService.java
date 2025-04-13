package com.example.task.service;

import com.example.task.dto.request.*;
import com.example.task.dto.response.*;
import com.example.task.model.Tasks;
import com.example.task.model.Users;
import com.example.task.repository.StageRepo;
import com.example.task.repository.TasksRepo;
import com.example.task.repository.UsersRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TaskService {

    @Autowired
    TasksRepo tasksRepo;
    @Autowired
    UsersRepo usersRepo;

    @Autowired
    StageRepo stageRepo;

    private final String completedTaskName = "Completed";

    // add task
    public ResponseEntity<BaseResponse> addTask(AddTaskRequest request, Users user) {
        BaseResponse response = new BaseResponse();
        try {
            var task = Tasks.builder()
                    .taskName(request.getTaskName())
                    .taskDetail(request.getTaskDetail())
                    .user(user)
                    .createdAt(LocalDateTime.now())
                    .build();
            var newTask = tasksRepo.save(task);

            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Success add new task");
            response.setData(BasicTaskResponse.builder().taskId(newTask.getTaskId()).build());
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
    public ResponseEntity<BaseResponse> editTask(EditTaskRequest request, String taskId, String userId) {
        BaseResponse response = new BaseResponse();
        try {
            var task = tasksRepo.findById(taskId).orElse(null);
            if(task == null) throw new Exception("Task not found");

            if(!task.getUser().getUserId().equals(userId)) throw new Exception("User don't have permission for this task");
            task.setTaskName(request.getTaskName());
            task.setTaskDetail(request.getTaskDetail());

            tasksRepo.save(task);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Success edit task");
            response.setData(BasicTaskResponse.builder().taskId(task.getTaskId()).build());

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
    public ResponseEntity<BaseResponse> removeTask(String userId, String taskId) {
        BaseResponse response = new BaseResponse();
        try {
            var task = tasksRepo.findById(taskId).orElse(null);
            if(task == null) throw new Exception("Task not found");

            if(!task.getUser().getUserId().equals(userId)) throw new Exception("User don't have permission for this task");

            task.setDeletedAt(LocalDateTime.now());
            tasksRepo.save(task);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Success remove task");
            response.setData(BasicTaskResponse.builder().taskId(task.getTaskId()).build());

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
    public ResponseEntity<BaseResponse> updateTask(UpdateTaskRequest request, String taskId, String userId) {
        BaseResponse response = new BaseResponse();
        try {
            var task = tasksRepo.findById(taskId).orElse(null);
            if(task == null) throw new Exception("Task not found");

            if(!task.getUser().getUserId().equals(userId)) throw new Exception("User don't have permission for this task");

            var stage = stageRepo.findById(request.getStageId()).orElse(null);
            if(stage == null) throw new Exception("Stage not found");
            task.setStage(stage);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Success update task");
            response.setData(UpdateTaskResponse.builder().taskId(task.getTaskId())
                            .updatedStageId(stage.getStageId())
                            .updatedStageName(stage.getStageName())
                    .build());

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
    public ResponseEntity<BaseResponse> completeTask(String userId, String taskId) {
        BaseResponse response = new BaseResponse();
        try {
            var task = tasksRepo.findById(taskId).orElse(null);
            if(task == null) throw new Exception("Task not found");

            if(!task.getUser().getUserId().equals(userId)) throw new Exception("User don't have permission for this task");

            var stage = stageRepo.findByStageName(completedTaskName).orElse(null);
            if(stage == null) throw new Exception("Stage not found");
            task.setStage(stage);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Success complete task");
            response.setData(UpdateTaskResponse.builder().taskId(task.getTaskId())
                    .updatedStageId(stage.getStageId())
                    .updatedStageName(stage.getStageName())
                    .build());
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

    // get specific task
    public ResponseEntity<BaseResponse> getSpecificTask(String userId, String taskId) {
        BaseResponse response = new BaseResponse();
        try {
            var task = tasksRepo.findById(taskId).orElse(null);
            if(task == null) throw new Exception("Task not found");

            if(!task.getUser().getUserId().equals(userId)) throw new Exception("User don't have permission for this task");

            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Success retrieve task detail");
            response.setData(TaskDetailResponse.builder()
                    .taskId(task.getTaskId())
                    .taskName(task.getTaskName())
                    .taskDetail(task.getTaskDetail())
                    .stageId(task.getStage().getStageId())
                    .stageName(task.getStage().getStageName())
                    .build());

            return ResponseEntity.ok(response);
        }
        catch(Exception e) {
            log.error("Error in getSpecificTask, message : " + e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Something wrong happened");
            response.setData(null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    // get all, incomplete and complete task
    public ResponseEntity<BaseResponse> getAllTask(String userId, String allTaskType) {
        BaseResponse response = new BaseResponse();
        try {
            var user = usersRepo.findById(userId).orElse(null);
            if(user == null) throw new Exception("User not found");

            List<Tasks> tasks = new ArrayList<>();
            if(allTaskType.equals("All")) tasks = tasksRepo.findByUserId(user.getUserId());
            else if(allTaskType.equals("Incomplete")) tasks = tasksRepo.findOnlyIncompleteByUserId(user.getUserId());
            else if(allTaskType.equals("Complete")) tasks = tasksRepo.findOnlyCompleteByUserId(user.getUserId());

            List<TaskDetailResponse> details = new ArrayList<>();
            for(var t : tasks) {
                var detail = TaskDetailResponse.builder()
                        .taskId(t.getTaskId())
                        .taskName(t.getTaskName())
                        .taskDetail(t.getTaskDetail())
                        .stageId(t.getStage().getStageId())
                        .stageName(t.getStage().getStageName())
                        .build();
                details.add(detail);
            }
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Success retrieve all task");
            response.setData(AllTaskResponse.builder()
                    .userId(user.getUserId())
                    .tasks(details)
                    .build());
            return ResponseEntity.ok(response);
        }
        catch(Exception e) {
            log.error("Error in getAllTask, message : " + e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Something wrong happened");
            response.setData(null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

}
