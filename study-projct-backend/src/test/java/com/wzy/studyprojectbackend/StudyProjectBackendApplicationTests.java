package com.wzy.studyprojectbackend;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wzy.studyprojectbackend.entity.domain.Account;
import com.wzy.studyprojectbackend.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;


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


    @Test
    void testAuth(){
        LambdaQueryWrapper<Account> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Account::getUserName, "admin")
                .or()
                .eq(Account::getEmail, "admin");

        Account selectOne = userMapper.selectOne(wrapper);
        System.out.println(selectOne);
    }

    @Resource
    StringRedisTemplate template;

    @Test
    void redis(){
        template.opsForValue().set("name", "hhh");
        String name = template.opsForValue().get("name");
        System.out.println(name);
        if (name != null) {
            System.out.println("redis测试成功");
        }
    }

}
