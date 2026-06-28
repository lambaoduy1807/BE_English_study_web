package com.english_study.config.dbmigrations;

import com.english_study.model.entity.UserEntity;
import com.english_study.model.entity.VocabSetEntity;
import com.english_study.model.entity.UserVocabSet;
import com.english_study.model.entity.Word;
import com.english_study.model.entity.Video;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Arrays;

@ChangeUnit(id = "init-db", order = "001", author = "Duy")
public class V1__InitialSetup {

    @Execution
    public void execution(MongoTemplate mongoTemplate) {
        // 1. Insert Vocab Sets
        VocabSetEntity vs1 = VocabSetEntity.builder()
                .id("vs1")
                .title("IELTS Level 1")
                .numOfWords(500)
                .icon("auto_stories")
                .build();

        VocabSetEntity vs2 = VocabSetEntity.builder()
                .id("vs2")
                .title("TOEIC 650+")
                .numOfWords(850)
                .icon("school")
                .build();

        VocabSetEntity vs3 = VocabSetEntity.builder()
                .id("vs3")
                .title("Travel Phrases")
                .numOfWords(200)
                .icon("travel_explore")
                .build();

        mongoTemplate.insertAll(Arrays.asList(vs1, vs2, vs3));

        // 2. Insert Users
        UserEntity u1 = UserEntity.builder()
                .id("u1")
                .username("nguyenvana")
                .password("$2a$10$abcdefghijklmnopqrstuv") // dummy hash
                .email("vana@gmail.com")
                .fullName("Nguyen Van A")
                .isDelete(false)
                .avatar("avatar1.png")
                .roleId("USER")
                .level("B1")
                .totalXP(100)
                .myVocabs(Arrays.asList("vs1", "vs2"))
                .build();
        
        UserEntity u2 = UserEntity.builder()
                .id("u2")
                .username("admin")
                .password("$2a$10$xyzabcdefghijklmnopqrs") // dummy hash
                .email("admin@gmail.com")
                .fullName("System Admin")
                .isDelete(false)
                .avatar("admin.png")
                .roleId("ADMIN")
                .level("C1")
                .totalXP(500)
                .build();
        mongoTemplate.insertAll(Arrays.asList(u1, u2));

        // 3. Insert Words
        Word w1 = Word.builder().id("w1").word("Accommodate").mean("Cung cấp chỗ ở, đáp ứng").type("Verb").example("The hotel can accommodate up to 500 guests.").vocabID("vs1").build();
        Word w2 = Word.builder().id("w2").word("Benefit").mean("Lợi ích").type("Noun").example("The benefits of exercise are well documented.").vocabID("vs1").build();
        Word w3 = Word.builder().id("w3").word("Candidate").mean("Ứng cử viên").type("Noun").example("She is the best candidate for the job.").vocabID("vs1").build();
        Word w4 = Word.builder().id("w4").word("Analyze").mean("Phân tích").type("Verb").example("We need to analyze the data.").vocabID("vs2").build();
        Word w5 = Word.builder().id("w5").word("Assume").mean("Giả sử").type("Verb").example("I assume you are right.").vocabID("vs2").build();
        Word w6 = Word.builder().id("w6").word("Airport").mean("Sân bay").type("Noun").example("We arrived at the airport early.").vocabID("vs3").build();
        Word w7 = Word.builder().id("w7").word("Luggage").mean("Hành lý").type("Noun").example("Do not leave your luggage unattended.").vocabID("vs3").build();
        Word w8 = Word.builder().id("w8").word("Passport").mean("Hộ chiếu").type("Noun").example("Show your passport at the counter.").vocabID("vs3").build();
        mongoTemplate.insertAll(Arrays.asList(w1, w2, w3, w4, w5, w6, w7, w8));

        // 4. Insert UserVocabSet (progress)
        UserVocabSet uvs1 = UserVocabSet.builder()
                .id("uvs1")
                .userID("u1")
                .vocabID("vs1")
                .learningProgress(50)
                .memoryWords(Arrays.asList("w1", "w2"))
                .build();
        UserVocabSet uvs2 = UserVocabSet.builder()
                .id("uvs2")
                .userID("u1")
                .vocabID("vs2")
                .learningProgress(20)
                .memoryWords(Arrays.asList("w4"))
                .build();
        mongoTemplate.insertAll(Arrays.asList(uvs1, uvs2));

        // 5. Insert Videos
        Video v1 = Video.builder().id("v1").title("Learn English in 30 Minutes").url("https://youtube.com/watch?v=123").upload_date(java.util.Date.from(java.time.Instant.parse("2023-10-01T00:00:00Z"))).viewCount(15000).build();
        Video v2 = Video.builder().id("v2").title("Basic English Conversation").url("https://youtube.com/watch?v=456").upload_date(java.util.Date.from(java.time.Instant.parse("2023-10-05T00:00:00Z"))).viewCount(23000).build();
        mongoTemplate.insertAll(Arrays.asList(v1, v2));
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
