package com.example.task.controller;

import com.example.task.dto.response.BaseResponse;
import com.example.task.service.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stage")
public class StageController {

    @Autowired
    private StageService stageService;

    @GetMapping
    public ResponseEntity<BaseResponse> getAllStages() {
        return stageService.getAllStage();
    }
}
