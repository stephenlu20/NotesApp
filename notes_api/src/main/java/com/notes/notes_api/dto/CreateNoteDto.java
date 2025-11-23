package com.notes.notes_api.dto;

import java.util.List;

public class CreateNoteDto {

    private String title;  // required
    private String author; // required
    private String content; // optional
    private List<String> tags; // optional

    // Getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
}
