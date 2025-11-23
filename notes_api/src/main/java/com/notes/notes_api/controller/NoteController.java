package com.notes.notes_api.controller;

import com.notes.notes_api.entity.Note;
import com.notes.notes_api.entity.NoteStatus;
import com.notes.notes_api.dto.*;
import com.notes.notes_api.repository.NoteRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/notes")
@CrossOrigin(origins = "http://localhost:5173")
public class NoteController {

    private final NoteRepository noteRepository;

    public NoteController(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    // Get all notes
    @GetMapping
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        return noteRepository.findById(id)
                .map(ResponseEntity::ok)                    // return 200 OK with note
                .orElse(ResponseEntity.notFound().build()); // return 404 if not found
    }

    @PostMapping
    public Note createNote(@RequestBody CreateNoteDto createNoteDto) {
        if (createNoteDto.getTitle() == null || createNoteDto.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title is required");
        }
        if (createNoteDto.getAuthor() == null || createNoteDto.getAuthor().isBlank()) {
            throw new IllegalArgumentException("Author is required");
        }

        Note note = new Note();
        note.setTitle(createNoteDto.getTitle());
        note.setAuthor(createNoteDto.getAuthor());

        // Optional fields
        note.setContent(createNoteDto.getContent() != null ? createNoteDto.getContent() : "");
        note.setTags(createNoteDto.getTags() != null ? createNoteDto.getTags() : List.of());

        // Priority, Status, Created, Modified all use defaults from entity
        return noteRepository.save(note);
    }

    @PostMapping("/batch")
    public List<Note> createNotesBatch(@RequestBody List<CreateNoteDto> noteDtos) {
        if (noteDtos == null || noteDtos.isEmpty()) {
            throw new IllegalArgumentException("Request body must be a non-empty list of notes");
        }

        List<Note> notesToSave = noteDtos.stream().map(dto -> {
            if (dto.getTitle() == null || dto.getTitle().isBlank()) {
                throw new IllegalArgumentException("Each note must have a title");
            }
            if (dto.getAuthor() == null || dto.getAuthor().isBlank()) {
                throw new IllegalArgumentException("Each note must have an author");
            }

            Note note = new Note();
            note.setTitle(dto.getTitle());
            note.setAuthor(dto.getAuthor());
            note.setContent(dto.getContent() != null ? dto.getContent() : "");
            note.setTags(dto.getTags() != null ? dto.getTags() : List.of());
            // Priority, Status, Created, Modified use defaults
            return note;
        }).toList();

        return noteRepository.saveAll(notesToSave);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Note> updateNote(
            @PathVariable Long id,
            @RequestBody UpdateNoteDto dto) {

        return noteRepository.findById(id).map(existingNote -> {

            if (dto.getTitle() != null && !dto.getTitle().isBlank()) {
                existingNote.setTitle(dto.getTitle());
            }
            if (dto.getAuthor() != null && !dto.getAuthor().isBlank()) {
                existingNote.setAuthor(dto.getAuthor());
            }
            if (dto.getTags() != null) {
                existingNote.setTags(dto.getTags());
            }
            if (dto.getPriority() != null) {
                existingNote.setPriority(dto.getPriority());
            }
            if (dto.getContent() != null) {
                existingNote.setContent(dto.getContent());
            }

            existingNote.setModified(LocalDateTime.now().toString());
            Note updatedNote = noteRepository.save(existingNote);
            return ResponseEntity.ok(updatedNote);

        }).orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<Note> toggleStatus(@PathVariable Long id) {
        return noteRepository.findById(id).map(note -> {
            NoteStatus current = note.getStatus();
            if (current == NoteStatus.COMPLETE) {
                note.setStatus(NoteStatus.REVIEW);
            } else {
                note.setStatus(NoteStatus.COMPLETE);
            }

            note.setModified(LocalDateTime.now().toString());
            Note updated = noteRepository.save(note);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        if (!noteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        noteRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // 204 - success, no body returned
    }
}
