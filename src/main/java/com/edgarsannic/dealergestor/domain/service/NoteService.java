/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.edgarsannic.dealergestor.domain.service;

import com.edgarsannic.dealergestor.domain.model.Note;

import java.util.List;

public interface NoteService {

    List<Note> findAllNotes();
    Note findNoteById(Long id);
    Note saveNote(Note request);
    Note updateNote(Long id, Note request);
    void deleteNote(Long id);
}