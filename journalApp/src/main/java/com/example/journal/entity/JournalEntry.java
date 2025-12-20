package com.example.journal.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;


@Document
public class JournalEntry {
    private  Long id;
    private String title;
    private String content;

    JournalEntry(){};

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
