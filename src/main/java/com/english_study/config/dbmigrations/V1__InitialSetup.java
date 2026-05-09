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

import java.sql.Date;
import java.util.Arrays;

@ChangeUnit(id = "init-db", order = "001", author = "Duy")
public class V1__InitialSetup {

    @Execution
    public void execution(MongoTemplate mongoTemplate) {
        // 1. Insert Vocab Sets
        VocabSetEntity vs1 = VocabSetEntity.builder().id("vs1").name("TOEIC 600").numOfWords(3).build();
        VocabSetEntity vs2 = VocabSetEntity.builder().id("vs2").name("IELTS Basic").numOfWords(2).build();
        mongoTemplate.insertAll(Arrays.asList(vs1, vs2));

        // 2. Insert Users
        UserEntity u1 = UserEntity.builder()
                .id("u1")
                .name("nguyenvana")
                .password("$2a$10$abcdefghijklmnopqrstuv") // dummy hash
                .email("vana@gmail.com")
                .fulltName("Nguyen Van A")
                .isDelete(false)
                .avatar("avatar1.png")
                .roleId("USER")
                .level("B1")
                .totalXP(100)
                .my_vocabs(Arrays.asList("vs1", "vs2"))
                .build();
        
        UserEntity u2 = UserEntity.builder()
                .id("u2")
                .name("admin")
                .password("$2a$10$xyzabcdefghijklmnopqrs") // dummy hash
                .email("admin@gmail.com")
                .fulltName("System Admin")
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
        mongoTemplate.insertAll(Arrays.asList(w1, w2, w3, w4, w5));

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
        Video v1 = Video.builder().id("v1").title("Learn English in 30 Minutes").url("https://youtube.com/watch?v=123").upload_date(Date.valueOf("2023-10-01")).viewCount(15000).build();
        Video v2 = Video.builder().id("v2").title("Basic English Conversation").url("https://youtube.com/watch?v=456").upload_date(Date.valueOf("2023-10-05")).viewCount(23000).build();
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
