/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.edgarsannic.dealergestor.domain.service;

import com.edgarsannic.dealergestor.domain.entity.CompanyUserEntity;
import com.edgarsannic.dealergestor.domain.entity.NoteEntity;
import com.edgarsannic.dealergestor.domain.model.Note;
import com.edgarsannic.dealergestor.domain.repository.NoteRepository;
import com.edgarsannic.dealergestor.utils.ModelMapperUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl implements NoteService{

    private final NoteRepository noteRepository;
    private final ModelMapperUtil modelMapperUtil;

    public NoteServiceImpl(NoteRepository noteRepository, ModelMapperUtil modelMapperUtil) {
        this.noteRepository = noteRepository;
        this.modelMapperUtil = modelMapperUtil;
    }

    @Override
    public List<Note> findAllNotes() {
        CompanyUserEntity companyUserEntity = new CompanyUserEntity();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CompanyUserEntity) {
            companyUserEntity = (CompanyUserEntity) authentication.getPrincipal();
        }

        return noteRepository.findByCompanyUserEntity(companyUserEntity).stream()
                .map(modelMapperUtil::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Note findNoteById(Long id) {
        CompanyUserEntity companyUserEntity = new CompanyUserEntity();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CompanyUserEntity) {
            companyUserEntity = (CompanyUserEntity) authentication.getPrincipal();
        }

        // Buscar la nota por ID
        NoteEntity noteEntity = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        // Verificar si la nota pertenece al usuario autenticado
        if (noteEntity.getCompanyUserEntity() != null && noteEntity.getCompanyUserEntity().getCompanyUserId().equals(companyUserEntity.getCompanyUserId())) {
            return modelMapperUtil.toModel(noteEntity);
        } else {
            throw new RuntimeException("Note does not belong to the authenticated user");
        }
    }


    @Override
    public Note saveNote(Note model) {
        CompanyUserEntity companyUserEntity = new CompanyUserEntity();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CompanyUserEntity) {
            companyUserEntity = (CompanyUserEntity) authentication.getPrincipal();
        }


        NoteEntity noteEntity = new NoteEntity();
        noteEntity.setTitle(model.getTitle());
        noteEntity.setContent(model.getContent());
        noteEntity.setCreatedAt(LocalDateTime.now());
        noteEntity.setCompanyUserEntity(companyUserEntity);

        return modelMapperUtil.toModel(noteRepository.save(noteEntity));
    }

    @Override
    public Note updateNote(Long id, Note updatedNote) {
        CompanyUserEntity companyUserEntity = new CompanyUserEntity();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CompanyUserEntity) {
            companyUserEntity = (CompanyUserEntity) authentication.getPrincipal();
        }

        // Buscar la nota existente por ID
        NoteEntity existingNote = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found with ID: " + id));

        // Verificar si la nota pertenece al usuario autenticado
        if (existingNote.getCompanyUserEntity() != null && existingNote.getCompanyUserEntity().getCompanyUserId().equals(companyUserEntity.getCompanyUserId())) {
            // Actualizar los campos de la nota
            existingNote.setTitle(updatedNote.getTitle());
            existingNote.setContent(updatedNote.getContent());

            NoteEntity saved = noteRepository.save(existingNote);
            return modelMapperUtil.toModel(saved);
        } else {
            throw new RuntimeException("Note does not belong to the authenticated user");
        }
    }


    @Override
    public void deleteNote(Long id) {
        CompanyUserEntity companyUserEntity = new CompanyUserEntity();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CompanyUserEntity) {
            companyUserEntity = (CompanyUserEntity) authentication.getPrincipal();
        }

        // Buscar la nota por ID
        NoteEntity existingNote = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found with ID: " + id));

        // Verificar si la nota pertenece al usuario autenticado
        if (existingNote.getCompanyUserEntity() != null && existingNote.getCompanyUserEntity().getCompanyUserId().equals(companyUserEntity.getCompanyUserId())) {
            noteRepository.deleteById(id);
        } else {
            throw new RuntimeException("Note does not belong to the authenticated user");
        }
    }

}