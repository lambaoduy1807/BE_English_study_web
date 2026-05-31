package com.english_study;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
        properties = {
                "spring.mongodb.uri=mongodb://localhost:27017/test"
        }
)
class EnvLoadingTest {

    @Value("${MONGO_URI:NOT_FOUND}")
    private String mongoUri;

    @Test
    void testEnv() {
        System.out.println("MONGO_URI = " + mongoUri);
    }
}