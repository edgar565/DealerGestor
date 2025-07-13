package com.edgarsannic.dealergestor.controller;

import com.edgarsannic.dealergestor.DealerGestorManager;
import com.edgarsannic.dealergestor.controller.ViewModel.NotePostViewModel;
import com.edgarsannic.dealergestor.controller.ViewModel.NoteViewModel;
import com.edgarsannic.dealergestor.utils.ViewModelMapperUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller responsible for managing notes.
 */
@RestController
@RequestMapping("/notes")
@Tag(name = "Notes", description = "Endpoints for managing notes")
public class NoteController {

    private final DealerGestorManager dealerGestorManager;
    private final ViewModelMapperUtil viewModelMapperUtil;

    public NoteController(DealerGestorManager dealerGestorManager, ViewModelMapperUtil viewModelMapperUtil) {
        this.dealerGestorManager = dealerGestorManager;
        this.viewModelMapperUtil = viewModelMapperUtil;
    }

    @Operation(summary = "Get all notes", description = "Retrieve a list of all notes")
    @ApiResponse(responseCode = "200", description = "List of notes retrieved successfully")
    @GetMapping
    public List<NoteViewModel> findAllNotes() {
        return dealerGestorManager.findAllNotes()
                .stream().map(viewModelMapperUtil::toViewModel)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get a note by ID", description = "Retrieve a specific note by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Note retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    @GetMapping("/{id}")
    public NoteViewModel findNoteById(@PathVariable Long id) {
        return viewModelMapperUtil.toViewModel(dealerGestorManager.findNoteById(id));
    }

    @Operation(summary = "Create a new note", description = "Save a new note in the system")
    @ApiResponse(responseCode = "201", description = "Note created successfully")
    @PostMapping("/save")
    public ResponseEntity<NoteViewModel> saveNote(@RequestBody NotePostViewModel notePostViewModel) {
        return ResponseEntity.ok(viewModelMapperUtil.toViewModel(dealerGestorManager.saveNote(viewModelMapperUtil.toModel(notePostViewModel))));
    }

    @Operation(summary = "Update a note", description = "Update an existing note by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Note updated successfully"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<NoteViewModel> updateNote(@PathVariable Long id, @RequestBody NotePostViewModel updatedNote) {
        return ResponseEntity.ok(viewModelMapperUtil.toViewModel(dealerGestorManager.updateNote(id, viewModelMapperUtil.toModel(updatedNote))));
    }

    @Operation(summary = "Delete a note", description = "Delete a note by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Note deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable Long id) {
        dealerGestorManager.deleteNote(id);
        return ResponseEntity.ok().build();
    }
}