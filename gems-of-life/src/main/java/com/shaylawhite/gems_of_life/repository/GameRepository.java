package com.shaylawhite.gems_of_life.repository;

import com.shaylawhite.gems_of_life.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}