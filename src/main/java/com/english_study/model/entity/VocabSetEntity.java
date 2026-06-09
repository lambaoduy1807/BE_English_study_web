package com.english_study.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "vocab_sets")
public class VocabSetEntity {
    @Id
    private String id;
    private String title;
    private int numOfWords;
    private String icon;
    private int categoryID;
    private String created_at;

}
