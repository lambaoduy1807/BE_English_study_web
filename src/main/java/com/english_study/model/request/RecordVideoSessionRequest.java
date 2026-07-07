package com.english_study.model.request;

import lombok.Data;

@Data
public class RecordVideoSessionRequest {
    private String videoId;
    private int correctScriptsCount;
}
