package com.english_study.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoDTO {
    private String id;
    private String title;
    private String url;
    private Date upload_date;
    private long viewCount;
    private long duration;
    private List<TranscriptItemDTO> transcripts;
}
