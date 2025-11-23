package com.notes.notes_api.dto;

import java.util.List;

public class NoteCreateDto {
    private String title;
    private String author;
    private List<String> tags;
    private String content;

    public NoteCreateDto() {}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}