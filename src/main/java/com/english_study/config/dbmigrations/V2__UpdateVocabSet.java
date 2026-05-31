package com.english_study.config.dbmigrations;

import com.english_study.model.entity.*;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Arrays;

@ChangeUnit(id = "updateVocabSet-db", order = "002", author = "Khoi")
public class V2__UpdateVocabSet {
    @Execution
    public void execution(MongoTemplate mongoTemplate) {
        // 1. Insert Vocab Sets
        VocabSetEntity vs1 = VocabSetEntity.builder()
                .id("s4")
                .title("IELTS Level 1")
                .numOfWords(500)
                .icon("auto_stories")
                .iconWrapClass("bg-secondary-container")
                .barClass("bg-secondary-container")
                .build();

        VocabSetEntity vs2 = VocabSetEntity.builder()
                .id("s5")
                .title("TOEIC 650+")
                .numOfWords(850)
                .icon("school")
                .iconWrapClass("bg-primary")
                .barClass("bg-primary")
                .build();

        VocabSetEntity vs3 = VocabSetEntity.builder()
                .id("s6")
                .title("Travel Phrases")
                .numOfWords(200)
                .icon("travel_explore")
                .iconWrapClass("bg-tertiary-container")
                .barClass("bg-tertiary-container")
                .build();

        mongoTemplate.insertAll(Arrays.asList(vs1, vs2, vs3));
    }
    @RollbackExecution
    public void rollbackExecution(MongoTemplate mongoTemplate) {
        mongoTemplate.remove(new org.springframework.data.mongodb.core.query.Query(), VocabSetEntity.class);
        mongoTemplate.remove(new org.springframework.data.mongodb.core.query.Query(), UserEntity.class);
        mongoTemplate.remove(new org.springframework.data.mongodb.core.query.Query(), Word.class);
        mongoTemplate.remove(new org.springframework.data.mongodb.core.query.Query(), UserVocabSet.class);
        mongoTemplate.remove(new org.springframework.data.mongodb.core.query.Query(), Video.class);
    }
}
