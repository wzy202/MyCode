package com.wzy.studyprojectbackend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wzy.studyprojectbackend.entity.domain.Account;
import com.wzy.studyprojectbackend.mapper.UserMapper;
import com.wzy.studyprojectbackend.service.AuthorizeService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class AuthorizeServiceImpl implements AuthorizeService {

    @Value("${spring.mail.username}")
    String from;

    @Resource
    private UserMapper userMapper;

    @Resource
    MailSender mailSender;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (username == null)
            throw new UsernameNotFoundException("用户名不能为空");

        // 去数据库查询数据
        LambdaQueryWrapper<Account> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Account::getUserName, username)
                .or()
                .eq(Account::getEmail, username);

        Account account = userMapper.selectOne(wrapper);

        if (account == null)
            throw new UsernameNotFoundException("用户名或密码错误");

        return User
                .withUsername(account.getUserName())
                .password(account.getPassword())
                .roles("user")
                .build();

    }


    /**
     * 1. 先生成对应的验证码 <br/>
     * 2. 把邮箱和对应的验证码直接放到Redis中(过期时间3分钟， 如果此时重新要求发送邮件，
     *      那么，只要时间低于2分钟， 就可以重新2发送一次，重复此流程。)<br/>
     * 3. 发送验证码到指定邮箱<br/>
     * 4. 如果发送失败，把Redis里面的刚刚插入的删除<br/>
     * 5. 用户在注册时，再从Redis里面取出对应键值对，然后看验证码是否一致<br/>
     * @param email 邮箱地址
     * @return 是否完成邮箱验证
     */
    @Override
    public boolean sendValidateEmail(String email, String sessionId) {
        String key = "email:"  + ":" + email;
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(key))){
            Long expire = Optional.ofNullable(stringRedisTemplate.getExpire(key, TimeUnit.SECONDS)).orElse(0L);
            if (expire > 120)   return false;
        }

        Random random = new Random();
        int code = random.nextInt(899999) + 100000;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("您的验证邮件");
        message.setText("验证码是："+ code);
        try {
            mailSender.send(message);

            ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
            stringStringValueOperations.set(key, String.valueOf(code), 3, TimeUnit.MINUTES);
            return true;
        } catch (MailException e) {
            e.printStackTrace();
            return false;
        }

    }
}
