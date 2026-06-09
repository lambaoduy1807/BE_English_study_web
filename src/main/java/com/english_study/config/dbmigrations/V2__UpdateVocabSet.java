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
                .build();

        VocabSetEntity vs2 = VocabSetEntity.builder()
                .id("s5")
                .title("TOEIC 650+")
                .numOfWords(850)
                .icon("school")
                .build();

        VocabSetEntity vs3 = VocabSetEntity.builder()
                .id("s6")
                .title("Travel Phrases")
                .numOfWords(200)
                .icon("travel_explore")
                .build();

        mongoTemplate.insertAll(Arrays.asList(vs1, vs2, vs3));

        // Insert Words for s4, s5, s6
        Word w1 = Word.builder().id("w9").word("Determine").mean("Xác định").type("Verb").example("We need to determine the cause.").vocabID("s4").build();
        Word w2 = Word.builder().id("w10").word("Significant").mean("Đáng kể, quan trọng").type("Adj").example("There is a significant difference.").vocabID("s4").build();
        Word w3 = Word.builder().id("w11").word("Negotiate").mean("Thương lượng").type("Verb").example("They will negotiate a new contract.").vocabID("s5").build();
        Word w4 = Word.builder().id("w12").word("Revenue").mean("Doanh thu").type("Noun").example("The company's revenue increased.").vocabID("s5").build();
        Word w5 = Word.builder().id("w13").word("Itinerary").mean("Lịch trình").type("Noun").example("Check your itinerary before the trip.").vocabID("s6").build();
        Word w6 = Word.builder().id("w14").word("Destination").mean("Điểm đến").type("Noun").example("Paris is a popular destination.").vocabID("s6").build();
        
        mongoTemplate.insertAll(Arrays.asList(w1, w2, w3, w4, w5, w6));
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
