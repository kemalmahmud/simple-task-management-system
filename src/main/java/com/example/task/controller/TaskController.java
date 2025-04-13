package com.example.task.controller;

import com.example.task.dto.request.*;
import com.example.task.dto.response.BaseResponse;
import com.example.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // add task
    @PostMapping("/add")
    public ResponseEntity<BaseResponse> addTask(@RequestBody AddTaskRequest request) {
        return taskService.addTask(request);
    }

    // edit task detail
    @PatchMapping("/{taskId}/edit")
    public ResponseEntity<BaseResponse> editTask(@PathVariable String taskId, @RequestBody EditTaskRequest request) {
        return taskService.editTask(request, taskId);
    }

    // remove task
    @PatchMapping("/{taskId}/delete")
    public ResponseEntity<BaseResponse> removeTask(@PathVariable String taskId, @RequestBody BasicTaskRequest request) {
        return taskService.removeTask(request, taskId);
    }

    // update task stage
    @PatchMapping("/{taskId}/update")
    public ResponseEntity<BaseResponse> updateTask(@PathVariable String taskId, @RequestBody UpdateTaskRequest request) {
        return taskService.updateTask(request, taskId);
    }

    // complete task
    @PatchMapping("/{taskId}/complete")
    public ResponseEntity<BaseResponse> completeTask(@PathVariable String taskId, @RequestBody BasicTaskRequest request) {
        return taskService.completeTask(request, taskId);
    }

    // get specific task
    @PostMapping("/{taskId}/detail")
    public ResponseEntity<BaseResponse> getTask(@PathVariable String taskId, @RequestBody BasicTaskRequest request) {
        return taskService.getSpecificTask(request, taskId);
    }

    // get all task
    @PostMapping("/all-task")
    public ResponseEntity<BaseResponse> getAllTasks(@RequestBody BasicTaskRequest request) {
        return taskService.getAllTask(request, "All");
    }

    // get incomplete task
    @PostMapping("/incomplete-task")
    public ResponseEntity<BaseResponse> getAllIncompleteTasks(@RequestBody BasicTaskRequest request) {
        return taskService.getAllTask(request, "Incomplete");
    }

    // get completed task
    @PostMapping("/completed-task")
    public ResponseEntity<BaseResponse> getAllCompletedTasks(@RequestBody BasicTaskRequest request) {
        return taskService.getAllTask(request, "Complete");
    }
}
