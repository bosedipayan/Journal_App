package com.example.journal.controller;

import com.example.journal.entity.JournalEntry;
import com.example.journal.service.JournalEntryService;
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

    @GetMapping
    public List<JournalEntry> getAllEntries() {
        return journalEntryService.getAllEntries();
    }

    @PostMapping
    public JournalEntry addEntry(@RequestBody JournalEntry entry) {
        entry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(entry);
        return entry;
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

    @DeleteMapping("delete/{id}")
    public void deleteEntryById(@PathVariable ObjectId id) {
        journalEntryService.deleteById(id);
    }

    @PutMapping("update/{id}")
    public JournalEntry updateEntry(@PathVariable ObjectId id, @RequestBody JournalEntry updatedEntry) {
        JournalEntry old = journalEntryService.findById(id).orElse(null);
        if (old != null) {
            old.setTitle(updatedEntry.getTitle() != null || updatedEntry.getTitle().isEmpty() ?
                    updatedEntry.getTitle() : old.getTitle());
            old.setContent(updatedEntry.getContent() != null || updatedEntry.getContent().isEmpty() ?
                    updatedEntry.getContent() : old.getContent());
            old.setDate(LocalDateTime.now());
        }
        journalEntryService.saveEntry(old);
        return old;
    }
}
