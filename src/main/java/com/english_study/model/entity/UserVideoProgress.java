package com.english_study.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "user_video_progress")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserVideoProgress {
    @Id
    private String id;
    private String userId;
    private String videoId;
    private int resumeAt; // Tính bằng giây (đang xem dở ở giây thứ mấy)
    private Date lastWatched; // Thời gian xem gần nhất
}
