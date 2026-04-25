package com.example.journal.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void testSendEmail() {
        redisTemplate.opsForValue().set("testKey", "testValue");
        String value = (String) redisTemplate.opsForValue().get("testKey");
        assert "testValue".equals(value);
    }
}
