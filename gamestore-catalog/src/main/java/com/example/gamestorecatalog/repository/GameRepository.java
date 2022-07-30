package com.example.gamestorecatalog.repository;

import com.example.gamestorecatalog.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findAllByStudio(String studio);
    List<Game>findAllByTitle(String title);
    List<Game> findAllByEsrbRating(String esrbRating);
}

