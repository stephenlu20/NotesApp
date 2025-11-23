package com.notes.notes_api.repository;

import com.notes.notes_api.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
