package com.example.journal.service;

import com.example.journal.response.WeatherResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T getValue(String key, Class<T> weatherResponseClass) {
         try{
             Object obj = redisTemplate.opsForValue().get(key);
             ObjectMapper mapper = new ObjectMapper();
             mapper.readValue(obj.toString(), weatherResponseClass);
         } catch (Exception e){
             log.error("Error while fetching value from Redis for key: " + key, e);
             return null;
         }
            return null;
    }

    public void setValue(String key, Object value, long ttl) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonValue = mapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(key, jsonValue, ttl, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("Error while setting value in Redis for key: " + key, e);
        }
    }
}
