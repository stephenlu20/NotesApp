package com.notes.notes_api.dto;

import com.notes.notes_api.entity.NoteStatus;

public class NoteStatusUpdateDto {
    private NoteStatus status;

    public NoteStatusUpdateDto() {}

    public NoteStatus getStatus() {
        return status;
    }

    public void setStatus(NoteStatus status) {
        this.status = status;
    }
}