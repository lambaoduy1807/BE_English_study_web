package com.english_study.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "icons")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class IconEntity {
    @Id
    private String id;
    private String name;
    private String code;
    
    @Builder.Default
    private Date createdAt = new Date();
}
