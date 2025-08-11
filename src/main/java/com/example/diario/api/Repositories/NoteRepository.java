package com.example.diario.api.Repositories;

import com.example.diario.api.Entities.NoteEntity;
import com.example.diario.api.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<NoteEntity,Long> {
    List<NoteEntity> findByTitleContainingIgnoreCase(String Title);
    List<NoteEntity> findByUser(UserEntity user);

    List<NoteEntity> findByTitleContainingIgnoreCaseAndUser(String title, UserEntity user);
}
