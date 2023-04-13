package com.wzy.studyprojectbackend;

import com.wzy.studyprojectbackend.entity.domain.Account;
import com.wzy.studyprojectbackend.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootTest
class StudyProjectBackendApplicationTests {

    @Resource
    PasswordEncoder encoder;

    @Resource
    UserMapper userMapper;

    @Test
    void contextLoads() {
        String password = encoder.encode("123456");
        System.out.println(password);
    }

    @Test
    void testMybatisPlus(){
        Account account = userMapper.selectById(1);
        System.out.println(account);
        System.out.println(encoder.matches("123456", account.getPassword()));
    }

}
