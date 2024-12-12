package com.shaylawhite.gems_of_life.model;


import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Represents a game of Gems of Life. This entity is stored in the database and contains
 * the core attributes and methods to manage a game session, including the secret combination,
 * remaining guesses, and the current game state.
 *
 * The game involves a secret combination of numbers that the player tries to guess,
 * with feedback provided after each guess. The game ends when the player guesses correctly
 * or runs out of remaining guesses.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity // Indicates that this class should be mapped to a table in the database
public class Game {

    /**
     * The unique identifier for this game. This ID is used as the primary key in the database.
     */
    @Id
    private Long id; // The primary key for the game entity

    /**
     * The secret combination of gems (numbers) that the player is trying to guess.
     * This would be stored as a list of integers or a String representation.
     */
    @ElementCollection

    private List<Integer> secretCombination;

    /**
     * The number of remaining guesses the player has.
     */
    private int remainingGuesses;

    /**
     * The current state of the game (e.g., "in-progress", "won", "lost").
     */
    private String gameState;

    // Additional methods or game logic can go here
}

