package com.example.diario.api.Services;


import com.example.diario.api.Entities.NoteEntity;
import com.example.diario.api.Entities.UserEntity;
import com.example.diario.api.Repositories.NoteRepository;
import com.example.diario.api.Repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    public NoteService(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }
    public List<NoteEntity> getAllNotes() {
        UserEntity user = getAuthenticatedUser();
        return noteRepository.findByUser(user);
    }

    public NoteEntity postNote(NoteEntity noteEntity) {
        noteEntity.setUser(getAuthenticatedUser());
        return noteRepository.save(noteEntity);
    }

    public List<NoteEntity> getByTitle(String title) {
        UserEntity user = getAuthenticatedUser();
        return noteRepository.findByTitleContainingIgnoreCaseAndUser(title, user);
    }

    public NoteEntity getNoteById(Long id) {
        UserEntity user = getAuthenticatedUser();
        NoteEntity note = noteRepository.findById(id).orElse(null);

        if (note != null && note.getUser().getId().equals(user.getId())) {
            return note;
        }
        return null; // ou lançar exceção se quiser
    }

    public List<NoteEntity> deleteById(Long id){
        noteRepository.deleteById(id);
        return getAllNotes();
    }
    public UserEntity getAuthenticatedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow();
    }

    public void updateNote(Long id, NoteEntity noteEntity){
        NoteEntity existingNote = getNoteById(id);
        if (existingNote != null) {
            existingNote.setTitle(noteEntity.getTitle());
            existingNote.setText(noteEntity.getText());
            existingNote.setImage(noteEntity.getImage());
            noteRepository.save(existingNote);
        }
    }

    public NoteEntity saveNote(NoteEntity noteEntity) {
        return noteRepository.save(noteEntity);
    }
}

