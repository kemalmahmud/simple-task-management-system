package com.example.task.service;

import com.example.task.dto.response.AllStageResponse;
import com.example.task.dto.response.BaseResponse;
import com.example.task.repository.StageRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class StageService {
    @Autowired
    StageRepo stageRepo;

    public ResponseEntity<BaseResponse> getAllStage() {
        BaseResponse response = new BaseResponse();
        try {
            var stages = stageRepo.findAll();
            List<AllStageResponse> allStages = new ArrayList<>();
            for(var s : stages) {
                var stage = AllStageResponse.builder()
                        .stageId(s.getStageId())
                        .stageName(s.getStageName())
                        .build();
                allStages.add(stage);
            }
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Success get stages");
            response.setData(allStages);

            return ResponseEntity.ok(response);
        }
        catch(Exception e) {
            log.error("Error in getAllStage, message : " + e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Something wrong happened");
            response.setData(null);
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
