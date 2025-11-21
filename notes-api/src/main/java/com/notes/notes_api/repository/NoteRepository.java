package com.notes.notes_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notes.notes_api.entity.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {
    Note findByTitle(String title);
    void deleteByTitle(String title);
}
