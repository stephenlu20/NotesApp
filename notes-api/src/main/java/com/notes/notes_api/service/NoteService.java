package com.notes.notes_api.service;

import com.notes.notes_api.entity.Note;
import com.notes.notes_api.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> listNotes() {
        return noteRepository.findAll();
    }

    public Note getNoteByTitle(String title) {
        return noteRepository.findByTitle(title);
    }

    public Note saveNote(Note note) {
        Instant now = Instant.now();
        if (note.getId() == null) {
            note.setCreated(now);
        }
        note.setModified(now);
        return noteRepository.save(note);
    }

    public Note updateContent(Note note, String newContent) {
        note.setContent(newContent);
        note.setModified(Instant.now()); // automatically update modified timestamp
        return noteRepository.save(note);
    }

    public void deleteNoteByTitle(String title) {
        noteRepository.deleteByTitle(title);
    }
}