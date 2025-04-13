package com.example.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDetailResponse {
    private String taskId;
    private String taskName;
    private String taskDetail;
    private Integer stageId;
    private String stageName;
}
