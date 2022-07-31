package com.example.gamestorecatalog.repository;

import com.example.gamestorecatalog.model.TShirt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TShirtRepository extends JpaRepository<TShirt, Long> {
    List<TShirt> findAllBySize(String size);
    List<TShirt> findAllByColor(String color);



}

