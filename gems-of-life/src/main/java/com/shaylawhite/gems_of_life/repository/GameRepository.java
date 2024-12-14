package com.shaylawhite.gems_of_life.repository;

import com.shaylawhite.gems_of_life.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
}
