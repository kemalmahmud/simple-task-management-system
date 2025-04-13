package com.example.task.controller;

import com.example.task.dto.request.*;
import com.example.task.dto.response.BaseResponse;
import com.example.task.model.Users;
import com.example.task.service.TaskService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // add task
    @PostMapping("/add")
    public ResponseEntity<BaseResponse> addTask(@RequestBody AddTaskRequest request, Authentication authentication) {
        Users user = (Users) authentication.getPrincipal();
        return taskService.addTask(request, user);
    }

    // edit task detail
    @PatchMapping("/{taskId}/edit")
    public ResponseEntity<BaseResponse> editTask(@PathVariable String taskId, @RequestBody EditTaskRequest request, Authentication authentication) {
        Users user = (Users) authentication.getPrincipal();
        return taskService.editTask(request, taskId, user.getUserId());
    }

    // remove task
    @PatchMapping("/{taskId}/delete")
    public ResponseEntity<BaseResponse> removeTask(@PathVariable String taskId, Authentication authentication) {
        Users user = (Users) authentication.getPrincipal();
        return taskService.removeTask(user.getUserId(), taskId);
    }

    // update task stage
    @PatchMapping("/{taskId}/update")
    public ResponseEntity<BaseResponse> updateTask(@PathVariable String taskId, @RequestBody UpdateTaskRequest request, Authentication authentication) {
        Users user = (Users) authentication.getPrincipal();
        return taskService.updateTask(request, taskId, user.getUserId());
    }

    // complete task
    @PatchMapping("/{taskId}/complete")
    public ResponseEntity<BaseResponse> completeTask(@PathVariable String taskId, Authentication authentication) {
        Users user = (Users) authentication.getPrincipal();
        return taskService.completeTask(user.getUserId(), taskId);
    }

    // get specific task
    @GetMapping("/{taskId}/detail")
    public ResponseEntity<BaseResponse> getTask(@PathVariable String taskId, Authentication authentication) {
        Users user = (Users) authentication.getPrincipal();
        return taskService.getSpecificTask(user.getUserId(), taskId);
    }

    // get all task
    @GetMapping("/all-task")
    public ResponseEntity<BaseResponse> getAllTasks(Authentication authentication) {
        Users user = (Users) authentication.getPrincipal();
        return taskService.getAllTask(user.getUserId(), "All");
    }

    // get incomplete task
    @GetMapping("/incomplete-task")
    public ResponseEntity<BaseResponse> getAllIncompleteTasks(Authentication authentication) {
        Users user = (Users) authentication.getPrincipal();
        return taskService.getAllTask(user.getUserId(), "Incomplete");
    }

    // get completed task
    @GetMapping("/completed-task")
    public ResponseEntity<BaseResponse> getAllCompletedTasks(Authentication authentication) {
        Users user = (Users) authentication.getPrincipal();
        return taskService.getAllTask(user.getUserId(), "Complete");
    }
}
