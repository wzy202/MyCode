package com.wzy.studyprojectbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wzy.studyprojectbackend.mapper")
public class StudyProjectBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyProjectBackendApplication.class, args);
    }

}
