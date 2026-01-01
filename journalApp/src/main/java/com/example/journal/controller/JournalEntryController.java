package com.example.journal.controller;

import com.example.journal.entity.JournalEntry;
import com.example.journal.entity.User;
import com.example.journal.service.JournalEntryService;
import com.example.journal.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("{username}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String username) {
        Optional<User> user = userService.findByUsername(username);
        List<JournalEntry> all = user.get().getJournalEntries();
        if(all != null && !all.isEmpty()) {
            return new  ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>("No journal entries found for user: " + username, HttpStatus.NOT_FOUND);
    }

    @PostMapping("{username}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry entry, @PathVariable String username) {
        try {
            entry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(entry, username);

            return new ResponseEntity<>(entry, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("find/{id}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable ObjectId id) {
        Optional<JournalEntry> entry = journalEntryService.findById(id);
//        return journalEntryService.findById(id);
        if(entry.isPresent())
        {
            return new ResponseEntity<>(entry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("delete/{username}/{id}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId id, @PathVariable String username) {
        journalEntryService.deleteById(id, username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("update/{username}/{id}")
    public JournalEntry updateEntry(@PathVariable ObjectId id, @RequestBody JournalEntry updatedEntry, @PathVariable String username) {
        JournalEntry old = journalEntryService.findById(id).orElse(null);
        if (old != null) {
            old.setTitle(updatedEntry.getTitle() != null || updatedEntry.getTitle().isEmpty() ?
                    updatedEntry.getTitle() : old.getTitle());
            old.setContent(updatedEntry.getContent() != null || updatedEntry.getContent().isEmpty() ?
                    updatedEntry.getContent() : old.getContent());
            old.setDate(LocalDateTime.now());
        }
        journalEntryService.updateEntry(old);
        return old;
    }
}
