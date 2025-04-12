package com.example.task.constant;

public enum StageEnum {
    TODO(1),
    IN_PROGRESS(2),
    COMPLETED(3);

    private int stageId;

    StageEnum(int id) { this.stageId = id; }
    public Integer getId() { return this.stageId; }
}
