package com.notes.notes_api.service;

import com.notes.notes_api.dto.NoteCreateDto;
import com.notes.notes_api.entity.Note;
import com.notes.notes_api.entity.NoteStatus;
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

    public Note createNote(NoteCreateDto dto) {
        Instant now = Instant.now();

        Note note = new Note();
        note.setTitle(dto.getTitle());
        note.setAuthor(dto.getAuthor());
        note.setTags(dto.getTags());
        note.setContent(dto.getContent());

        note.setCreated(now);
        note.setModified(now);

        return noteRepository.save(note);
    }

    public List<Note> listNotes() {
        return noteRepository.findAll();
    }

    public Note getNoteByTitle(String title) {
        return noteRepository.findByTitle(title);
    }

    public Note getNoteById(Long id) {
        return noteRepository.findById(id).orElse(null);
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
        note.setModified(Instant.now());
        return noteRepository.save(note);
    }

    public Note updateTitle(Note note, String newTitle) {
        note.setTitle(newTitle);
        note.setModified(Instant.now());
        return noteRepository.save(note);
    }

    public Note updateTags(Note note, List<String> newTags) {
        note.setTags(newTags);
        note.setModified(Instant.now());
        return noteRepository.save(note);
    }

    public Note updateAuthor(Note note, String newAuthor) {
        note.setAuthor(newAuthor);
        note.setModified(Instant.now());
        return noteRepository.save(note);
    }

    public Note updatePriority(Note note, int newPriority) {
        if (newPriority < 1 || newPriority > 5) {
            throw new IllegalArgumentException("Priority must be between 1 and 5");
        }
        note.setPriority(newPriority);
        note.setModified(Instant.now());
        return noteRepository.save(note);
    }

    public Note updateStatus(Note note, NoteStatus newStatus) {
        note.setStatus(newStatus);
        note.setModified(Instant.now());
        return noteRepository.save(note);
    }

    public boolean deleteNoteById(Long id) {
        if (!noteRepository.existsById(id)) {
            return false;
        }
        noteRepository.deleteById(id);
        return true;
    }
}