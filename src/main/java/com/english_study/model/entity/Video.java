package com.english_study.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "video")
public class Video {
    @Id
    private String id;
    private String title;
    private String url;
    private Date upload_date;
    private long viewCount;
    private long duration;
}
