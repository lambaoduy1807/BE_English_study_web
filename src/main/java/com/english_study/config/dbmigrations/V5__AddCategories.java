package com.english_study.config.dbmigrations;

import com.english_study.model.entity.CategoryEntity;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Arrays;
import java.util.List;

@ChangeUnit(id = "add-categories", order = "5", author = "beelish")
public class V5__AddCategories {

    @Execution
    public void changeSet(MongoTemplate mongoTemplate) {
        List<CategoryEntity> categories = Arrays.asList(
                CategoryEntity.builder().id(1).name("IELTS").build(),
                CategoryEntity.builder().id(2).name("TOEIC").build(),
                CategoryEntity.builder().id(3).name("Theo Level").build(),
                CategoryEntity.builder().id(4).name("Chuyên Ngành").build(),
                CategoryEntity.builder().id(5).name("Giao Tiếp").build(),
                CategoryEntity.builder().id(6).name("Ngữ Pháp").build()
        );

        for (CategoryEntity category : categories) {
            // Upsert or insert (using save handles both if @Id matches)
            mongoTemplate.save(category);
        }
    }

    @RollbackExecution
    public void rollback() {
        // Có thể để trống hoặc drop collection nếu cần thiết
    }
}
