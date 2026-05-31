package com.english_study.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VocabSetDTO {
    private String id;
    private String title;
    private int numOfWords;
    private String icon;
    private String iconWrapClass;
    private String barClass;
    private int categoryID;
}
