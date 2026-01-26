package com.example.journal.service;

import com.example.journal.entity.JournalEntry;
import com.example.journal.entity.User;
import com.example.journal.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService{

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry entry, String username) {
        User user = userService.findByUsername(username).orElse(null);
        entry.setDate(LocalDateTime.now());
        JournalEntry save = journalEntryRepository.save(entry);
        user.getJournalEntries().add(save);

        userService.saveEntry(user);
    }

    public void updateEntry(JournalEntry entry) {
        journalEntryRepository.save(entry);
    }

    public List<JournalEntry> getAllEntries() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String username) {
        boolean b=false;
        try {
            Optional<User> user = userService.findByUsername(username);
            b = user.get().getJournalEntries().removeIf(entry -> entry.getId().equals(id));
            if(b)
            {
                userService.saveEntry(user.get());
                journalEntryRepository.deleteById(id);
            }

        }
        catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occurred while deleting the journal entry.");
        }
        return b;
    }

    public List<JournalEntry> findByUsername(String username) {
        Optional<User> user = userService.findByUsername(username);
        if(user.isPresent()) {
            return user.get().getJournalEntries();
        }
        return new ArrayList<>();
    }

}