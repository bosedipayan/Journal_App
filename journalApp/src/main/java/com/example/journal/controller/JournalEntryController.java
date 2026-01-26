package com.example.journal.controller;

import com.example.journal.entity.JournalEntry;
import com.example.journal.entity.User;
import com.example.journal.service.JournalEntryService;
import com.example.journal.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Optional<User> user = userService.findByUsername(username);
        List<JournalEntry> all = user.get().getJournalEntries();
        if(all != null && !all.isEmpty()) {
            return new  ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>("No journal entries found for user: " + username, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry entry) {

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();

            entry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(entry, username);

            return new ResponseEntity<>(entry, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("find/{id}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable ObjectId myID) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional <User> user = userService.findByUsername(username);
        List<JournalEntry> collect = user.get().getJournalEntries().stream().filter(x -> x.getId().equals(myID)).collect(Collectors.toList());

//        return journalEntryService.findById(id);
        if(!collect.isEmpty())
        {
            Optional<JournalEntry> entry = journalEntryService.findById(myID);
            if(entry.isPresent())
            {
                return new ResponseEntity<>(entry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        boolean removed = journalEntryService.deleteById(id, username);
        if(removed)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

//
//        journalEntryService.deleteById(id, username);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("update/{username}/{id}")
    public JournalEntry updateEntry(@PathVariable ObjectId id, @RequestBody JournalEntry updatedEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional <User> user = userService.findByUsername(username);
        List<JournalEntry> collect = user.get().getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());

        if(!collect.isEmpty())
        {
            Optional<JournalEntry> byId = journalEntryService.findById(id);
            if(byId.isPresent())
            {
                JournalEntry old = byId.get();
                old.setTitle(updatedEntry.getTitle() != null || updatedEntry.getTitle().isEmpty() ?
                        updatedEntry.getTitle() : old.getTitle());
                old.setContent(updatedEntry.getContent() != null || updatedEntry.getContent().isEmpty() ?
                        updatedEntry.getContent() : old.getContent());
                old.setDate(LocalDateTime.now());
                journalEntryService.updateEntry(old);
                return old;
            }
        }
//        JournalEntry old = journalEntryService.findById(id).orElse(null);
//        if (old != null) {
//            old.setTitle(updatedEntry.getTitle() != null || updatedEntry.getTitle().isEmpty() ?
//                    updatedEntry.getTitle() : old.getTitle());
//            old.setContent(updatedEntry.getContent() != null || updatedEntry.getContent().isEmpty() ?
//                    updatedEntry.getContent() : old.getContent());
//            old.setDate(LocalDateTime.now());
//        }
//        journalEntryService.updateEntry(old);
//        return old;
        return null;
    }
}
