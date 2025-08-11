package com.example.diario.api.Controllers;



import com.example.diario.api.Entities.NoteEntity;
import com.example.diario.api.Services.NoteService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/notes")
@CrossOrigin("*")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService){
        this.noteService = noteService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public NoteEntity postNote(
            @RequestParam String title,
            @RequestParam String text,
            @RequestParam MultipartFile image
    ) throws IOException {
        NoteEntity noteEntity = new NoteEntity();
        noteEntity.setTitle(title);
        noteEntity.setText(text);
        noteEntity.setImage(image.getBytes());
        return noteService.postNote(noteEntity);
    }


    @GetMapping
    public List<NoteEntity> getAllNotes(){
        return noteService.getAllNotes();
    }

    @GetMapping("{id}")
    public NoteEntity getNoteById(@PathVariable Long id){
        return noteService.getNoteById(id);
    }

    @GetMapping("/buscar")
    public List<NoteEntity> getNoteByTitle(@RequestParam String title){
        return noteService.getByTitle(title);
    }

    @DeleteMapping("{id}")
    public List<NoteEntity> deleteById(@PathVariable Long id){
        return noteService.deleteById(id);
    }



    @PutMapping(value = "{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<NoteEntity> putNoteEntity(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String text,
            @RequestParam MultipartFile image
    ) throws IOException {
        NoteEntity noteEntity = new NoteEntity();
        noteEntity.setTitle(title);
        noteEntity.setText(text);
        noteEntity.setImage(image.getBytes());
        noteService.updateNote(id, noteEntity);
        return noteService.getAllNotes();
    }

}

