package com.shaylawhite.gems_of_life.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
@Entity
public class Game {

    // =======================
    // Entity Attributes
    // =======================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Automatically generates the primary key.
    private Long id;

    @ElementCollection
    private List<Integer> secretCombination;  // The secret combination of gems (numbers).

    private boolean isGameOver;
    private int remainingGuesses;  // The number of remaining guesses the player has.
    private String gameState;  // The current state of the game (e.g., "in-progress", "won", "lost").

    @ElementCollection
    private List<String> guessHistory;  // Tracks the history of guesses and feedback.

    @CreationTimestamp
    private LocalDateTime createdAt;  // Timestamp when the game was created.

    @UpdateTimestamp
    private LocalDateTime updatedAt;  // Timestamp when the game was last updated.

    // =======================
    // Constructors
    // =======================
    /**
     * Default constructor that initializes the game with default values.
     */
    public Game(List<Integer> secretCombination) {
        this.secretCombination = secretCombination;
        this.remainingGuesses = 10;  // Default number of guesses.
        this.guessHistory = new ArrayList<>();
        this.isGameOver = false;
        this.gameState = "in-progress";  // Default game state.
    }

    /**
     * Constructor with all arguments.
     */
    public Game(Long id, List<Integer> secretCombination, int remainingGuesses, String gameState, List<String> guessHistory) {
        this.id = id;
        this.secretCombination = secretCombination;
        this.remainingGuesses = remainingGuesses;
        this.gameState = gameState;
        this.guessHistory = guessHistory != null ? guessHistory : new ArrayList<>();
    }

    // =======================
    // Getter and Setter Methods
    // =======================
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Integer> getSecretCombination() {
        return secretCombination;
    }

    public void setSecretCombination(List<Integer> secretCombination) {
        this.secretCombination = secretCombination;
    }

    public int getRemainingGuesses() {
        return remainingGuesses;
    }

    public void setRemainingGuesses(int remainingGuesses) {
        this.remainingGuesses = remainingGuesses;
    }

    public String getGameState() {
        return gameState;
    }

    public void setGameState(String gameState) {
        this.gameState = gameState;
    }

    public List<String> getGuessHistory() {
        return guessHistory;
    }

    public void setGuessHistory(List<String> guessHistory) {
        this.guessHistory = guessHistory;
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
            gameState = "lost";  // Mark the game as lost when no guesses remain.
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
