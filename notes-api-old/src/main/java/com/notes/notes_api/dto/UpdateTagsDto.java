package com.notes.notes_api.dto;

import java.util.List;

public class UpdateTagsDto {
    private List<String> tags;
    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
}