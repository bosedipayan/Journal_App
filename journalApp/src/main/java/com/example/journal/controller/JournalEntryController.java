package com.example.journal.controller;

import com.example.journal.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @GetMapping
    public List<JournalEntry> getAllEntries() {
        return null;
    }

    @PostMapping
    public boolean addEntry(@RequestBody JournalEntry entry) {
        return true;
    }

    @GetMapping("find/{id}")
    public JournalEntry getEntryById(@PathVariable Long id) {
        return null;
    }

    @DeleteMapping("delete/{id}")
    public JournalEntry deleteEntryById(@PathVariable Long id) {
        return  null;
    }
}
