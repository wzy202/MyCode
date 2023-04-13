package com.wzy.studyprojctbackend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;

@SpringBootTest
class StudyProjctBackendApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(String.valueOf(StandardCharsets.UTF_8));
    }

}
