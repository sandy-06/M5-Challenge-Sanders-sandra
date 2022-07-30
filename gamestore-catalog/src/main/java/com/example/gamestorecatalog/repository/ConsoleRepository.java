package com.example.gamestorecatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.gamestorecatalog.model.Console;

import java.util.List;

@Repository
public interface ConsoleRepository extends JpaRepository<Console, Long> {
    List<Console> findAllByManufacturer(String manufacturer);
}
