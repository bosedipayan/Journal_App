package com.example.journal.controller;

import com.example.journal.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/_journal")
public class JournalEntryControllerV2 {
    private Map<Long, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAllEntries() {
        return  new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public boolean addEntry(@RequestBody JournalEntry entry) {
        journalEntries.put(entry.getId(), entry);
        return true;
    }

    @GetMapping("find/{id}")
    public JournalEntry getEntryById(@PathVariable Long id) {
        return journalEntries.get(id);
    }

    @DeleteMapping("delete/{id}")
    public JournalEntry deleteEntryById(@PathVariable Long id) {
        return journalEntries.remove(id);
    }
}
