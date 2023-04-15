package com.wzy.studyprojectbackend;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wzy.studyprojectbackend.entity.domain.Account;
import com.wzy.studyprojectbackend.mapper.UserMapper;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisKeyValueTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.concurrent.TimeUnit;


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
    StringRedisTemplate stringRedisTemplate;

    @Test
    void redis(){
        System.out.println("stringRedisTemplate--->");
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
//        opsForValue.set("name", "hhh", 100, TimeUnit.SECONDS);
//        String name = opsForValue.get("name");

//        System.out.println(name);
        System.out.println("存放时间为：" + stringRedisTemplate.getExpire("name", TimeUnit.SECONDS));
//        if (name != null) {
//            System.out.println("redis测试成功");
//
//        }


    }

    @Test
    void testLong(){
        Long num = null;
        System.out.println(num);
    }


    /**
     * 测试redis存储对象
     */
    @Test
    void testRedis_object() throws JsonProcessingException {
        Account account = userMapper.selectById(1);
        System.out.println(account);

        ObjectMapper objectMapper = new ObjectMapper();
        String objectString = objectMapper.writeValueAsString(account);
        System.out.println(objectString);

        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        opsForValue.set("account", objectString);
    }


    /**
     * 测试redis读取对象
     */
    @Test
    void testRedis_object_read() throws JsonProcessingException {

        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        String account = opsForValue.get("account");
        System.out.println(account);

        ObjectMapper objectMapper = new ObjectMapper();
        Account readValue = objectMapper.readValue(account, Account.class);
        System.out.println(readValue);


    }

    @Resource
    RedisTemplate<String, Account>  redisTemplate;

    /**
     * 测试redis存储对象 --- 通过RedisTemplate
     */
    @Test
    void testRedis_object_RedisTemplate() throws JsonProcessingException {

        redisTemplate.delete("wzy");

        Account account = new Account(2, "qqqqqqqq@qq.com", "wzy", "qqqqqq");
        saveAccount(account);

    }
    public void saveAccount(Account account) {
        redisTemplate.opsForValue().set(account.getUserName(), account);
    }

    /**
     * 测试redis读取对象 --- 通过RedisTemplate
     */
    @Test
    void testRead_object_RedisTemplate() throws JsonProcessingException {

        System.out.println(getAccount("wzy"));


    }
    public Account getAccount(String userName) {
        Account account = redisTemplate.opsForValue().get(userName);
        return account;
    }


}
