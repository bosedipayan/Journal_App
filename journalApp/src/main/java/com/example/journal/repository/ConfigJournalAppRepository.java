package com.example.journal.repository;

import com.example.journal.entity.ConfigJournalApp;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalApp, String> {
     ConfigJournalApp findByKey(String key);
     void deleteEntryByKey(String key);

}
