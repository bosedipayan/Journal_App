package com.example.journal.cache;

import com.example.journal.entity.ConfigJournalApp;
import com.example.journal.repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {
    public Map<String, String> APP_CACHE;

    @Autowired
    public ConfigJournalAppRepository configJournalAppRepository;

    @PostConstruct
    public void init(){
        APP_CACHE = new HashMap<>();
        List<ConfigJournalApp> all = configJournalAppRepository.findAll();
        for (var config: all) {
            APP_CACHE.put(config.getKey(), config.getValue());
        }
    }
}