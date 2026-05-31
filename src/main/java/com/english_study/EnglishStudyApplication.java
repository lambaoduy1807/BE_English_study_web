package com.english_study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.mongock.runner.springboot.EnableMongock;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@EnableMongock
@SpringBootApplication
@ConfigurationPropertiesScan
public class EnglishStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnglishStudyApplication.class, args);
    }

}
