package com.notes.notes_api.controller;
import com.notes.notes_api.dto.NoteContentUpdateDto;
import com.notes.notes_api.entity.Note;
import com.notes.notes_api.service.NoteService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<Note> getAllNotes() {
        return noteService.listNotes();
    }

    @GetMapping("/{title}")
    public ResponseEntity<Note> getNoteByTitle(@PathVariable String title) {
        Note note = noteService.getNoteByTitle(title);
        if (note == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(note);
    }

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        Note saved = noteService.saveNote(note);
        return ResponseEntity.created(
            URI.create("/notes/" + saved.getTitle())
        ).body(saved);
    }

    // inside NoteController.java
    @PutMapping("/{title}/content")
    public ResponseEntity<Note> updateNoteContent(
            @PathVariable String title,
            @RequestBody NoteContentUpdateDto dto) {

        Note existing = noteService.getNoteByTitle(title);
        if (existing == null) return ResponseEntity.notFound().build();

        Note updated = noteService.updateContent(existing, dto.getContent());
        return ResponseEntity.ok(updated);
}

    @DeleteMapping("/{title}")
    public ResponseEntity<Void> deleteNoteByTitle(@PathVariable String title) {
        Note existing = noteService.getNoteByTitle(title);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        noteService.deleteNoteByTitle(title);
        return ResponseEntity.noContent().build();
    }
}