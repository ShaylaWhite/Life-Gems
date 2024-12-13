package com.shaylawhite.gems_of_life.model;

import jakarta.persistence.*;
import lombok.Getter;           // Lombok to automatically generate getter methods.
import lombok.Setter;           // Lombok to automatically generate setter methods.
import lombok.NoArgsConstructor; // Lombok constructor with no arguments.
import lombok.AllArgsConstructor; // Lombok constructor with all arguments.

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;

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

    // =======================
    // Entity Attributes
    // =======================

    /**
     * The unique identifier for this game. This ID is used as the primary key in the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Automatically generates the primary key.

    private Long id; // The primary key for the game entity.

    /**
     * The secret combination of gems (numbers) that the player is trying to guess.
     * This would be stored as a list of integers.
     */
    @ElementCollection
    private List<Integer> secretCombination;  // List of integers representing the secret combination.

    /**
     * The number of remaining guesses the player has.
     */
    private int remainingGuesses;

    /**
     * The current state of the game (e.g., "in-progress", "won", "lost").
     */
    private String gameState;

    /**
     * A list that tracks the history of guesses and feedback.
     */
    @ElementCollection
    private List<String> guessHistory;

    // =======================
    // Constructors
    // =======================

    /**
     * Default constructor that initializes the game with default values.
     */
    public Game() {
        this.guessHistory = new ArrayList<>();
        this.gameState = "in-progress";
        this.remainingGuesses = 10;  // Example default value.
        this.secretCombination = new ArrayList<>();
    }

    // =======================
    // Helper Methods
    // =======================

    /**
     * Decreases the number of remaining guesses.
     * If no guesses are left, the game is marked as lost.
     */
    public void decreaseRemainingGuesses() {
        if (remainingGuesses > 0) {
            remainingGuesses--;
        }
        if (remainingGuesses == 0) {
            gameState = "lost"; // Mark the game as lost when no guesses remain.
        }
    }

    /**
     * Checks if the game is over.
     * The game is over if there are no remaining guesses or the game state is "won".
     *
     * @return true if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        return remainingGuesses == 0 || "won".equals(gameState);
    }

    /**
     * Adds a new guess and its feedback to the guess history.
     *
     * @param guess   The player's guess (formatted as a string).
     * @param feedback The feedback on the guess (formatted as a string).
     */
    public void addGuessHistory(String guess, String feedback) {
        guessHistory.add("Guess: " + guess + " - " + feedback);
    }

    // =======================
    // Overridden Methods
    // =======================

    /**
     * Returns a string representation of the current game state.
     * This can be helpful for debugging and displaying game status.
     *
     * @return A string representation of the game.
     */
    @Override
    public String toString() {
        return "Game{id=" + id +
                ", secretCombination=" + secretCombination +
                ", remainingGuesses=" + remainingGuesses +
                ", gameState='" + gameState + "'}";
    }
}

