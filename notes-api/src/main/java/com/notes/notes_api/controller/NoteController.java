package com.notes.notes_api.controller;
import com.notes.notes_api.dto.NoteContentUpdateDto;
import com.notes.notes_api.dto.NoteCreateDto;
import com.notes.notes_api.dto.NoteStatusUpdateDto;
import com.notes.notes_api.dto.UpdateAuthorDto;
import com.notes.notes_api.dto.UpdatePriorityDto;
import com.notes.notes_api.dto.UpdateTagsDto;
import com.notes.notes_api.dto.UpdateTitleDto;
import com.notes.notes_api.entity.Note;
import com.notes.notes_api.entity.NoteStatus;
import com.notes.notes_api.service.NoteService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.Instant;
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

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        Note note = noteService.getNoteById(id);
        if (note == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(note);
    }

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody NoteCreateDto dto) {
        Note saved = noteService.createNote(dto);
        return ResponseEntity
                .created(URI.create("/notes/" + saved.getTitle()))
                .body(saved);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Note> patchNote(
            @PathVariable Long id,
            @RequestBody NoteUpdateDto dto) {

        Note existing = noteService.getNoteById(id);
        if (existing == null) return ResponseEntity.notFound().build();

        // Only update fields that are not null in the request
        if (dto.getTitle() != null) existing.setTitle(dto.getTitle());
        if (dto.getContent() != null) existing.setContent(dto.getContent());
        if (dto.getTags() != null) existing.setTags(dto.getTags());
        if (dto.getAuthor() != null) existing.setAuthor(dto.getAuthor());
        if (dto.getPriority() != null) existing.setPriority(dto.getPriority());
        if (dto.getStatus() != null) existing.setStatus(dto.getStatus());

        // Always update modified timestamp
        existing.setModified(Instant.now());

        Note updated = noteService.save(existing);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateContent(
            @PathVariable long id,
            @RequestBody NoteContentUpdateDto dto) {

        Note existing = noteService.getNoteById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        Note updated = noteService.updateContent(existing, dto.getContent());
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/title")
    public ResponseEntity<Note> updateTitle(
            @PathVariable Long id,
            @RequestBody UpdateTitleDto dto) {

        Note existing = noteService.getNoteById(id);
        if (existing == null) return ResponseEntity.notFound().build();

        Note updated = noteService.updateTitle(existing, dto.getTitle());
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/tags")
    public ResponseEntity<Note> updateTags(
            @PathVariable Long id,
            @RequestBody UpdateTagsDto dto) {

        Note existing = noteService.getNoteById(id);
        if (existing == null) return ResponseEntity.notFound().build();

        Note updated = noteService.updateTags(existing, dto.getTags());
        return ResponseEntity.ok(updated);
    }
    
    @PutMapping("/{id}/author")
    public ResponseEntity<Note> updateAuthor(
            @PathVariable Long id,
            @RequestBody UpdateAuthorDto dto) {

        Note existing = noteService.getNoteById(id);
        if (existing == null) return ResponseEntity.notFound().build();

        Note updated = noteService.updateAuthor(existing, dto.getAuthor());
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/priority")
    public ResponseEntity<Note> updatePriority(
            @PathVariable Long id,
            @RequestBody UpdatePriorityDto dto) {

        Note existing = noteService.getNoteById(id);
        if (existing == null) return ResponseEntity.notFound().build();

        try {
            Note updated = noteService.updatePriority(existing, dto.getPriority());
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Note> toggleStatus(@PathVariable Long id) {
        Note existing = noteService.getNoteById(id);
        if (existing == null) return ResponseEntity.notFound().build();

        // Toggle REVIEW <-> COMPLETE
        NoteStatus newStatus = existing.getStatus() == NoteStatus.REVIEW
                ? NoteStatus.COMPLETE
                : NoteStatus.REVIEW;

        Note updated = noteService.updateStatus(existing, newStatus);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable long id) {
        boolean deleted = noteService.deleteNoteById(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}