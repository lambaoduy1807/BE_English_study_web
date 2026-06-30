package com.english_study.config.dbmigrations;

import com.english_study.model.entity.IconEntity;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Arrays;

@ChangeUnit(id = "add-icons", order = "006", author = "Duy")
public class V6__AddIcons {

    @Execution
    public void execution(MongoTemplate mongoTemplate) {
        IconEntity icon1 = IconEntity.builder().id("icon1").name("Trường học").code("school").build();
        IconEntity icon2 = IconEntity.builder().id("icon2").name("Từ vựng").code("auto_stories").build();
        IconEntity icon3 = IconEntity.builder().id("icon3").name("Du lịch").code("travel_explore").build();
        IconEntity icon4 = IconEntity.builder().id("icon4").name("Ngôi nhà").code("home").build();
        IconEntity icon5 = IconEntity.builder().id("icon5").name("Ngôi sao").code("star").build();
        IconEntity icon6 = IconEntity.builder().id("icon6").name("Lửa (Hot)").code("local_fire_department").build();
        IconEntity icon7 = IconEntity.builder().id("icon7").name("Cúp (Thành tích)").code("emoji_events").build();
        IconEntity icon8 = IconEntity.builder().id("icon8").name("Sách").code("book").build();

        mongoTemplate.insertAll(Arrays.asList(icon1, icon2, icon3, icon4, icon5, icon6, icon7, icon8));
    }

    @RollbackExecution
    public void rollbackExecution(MongoTemplate mongoTemplate) {
        mongoTemplate.remove(new org.springframework.data.mongodb.core.query.Query(), IconEntity.class);
    }
}
