package com.example.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateTaskResponse {
    private String taskId;
    private Integer updatedStageId;
    private String updatedStageName;
}
