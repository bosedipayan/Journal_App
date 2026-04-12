package com.example.journal.service;

import com.example.journal.cache.AppCache;
import com.example.journal.entity.User;
import com.example.journal.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    private static final String apiKey = "a7ebeec8d8c04b5114a87a8723e62f62";
//    private static final String apiUrl = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    public WeatherResponse getWeather(String city) {
        String finalAPI = appCache.APP_CACHE.get("weather_api").replace("API_KEY", apiKey).replace("CITY", city);

        String requestBody = "{}";

        User userBuild = User.builder().username("test").password("test").build();
        HttpEntity<User> requestEntity = new HttpEntity<>(userBuild);

        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.POST, requestEntity,
                WeatherResponse.class);
        WeatherResponse body = response.getBody();
        return body;
    }
}