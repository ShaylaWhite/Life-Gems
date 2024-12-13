package com.shaylawhite.gems_of_life.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<Integer> secretCombination;

    private boolean isGameOver;
    private int remainingGuesses;
    private String gameState;

    @ElementCollection
    private List<String> guessHistory;

    @ElementCollection
    private List<String> lifeLessons;  // New field for storing life lessons

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Game() {
        this.guessHistory = new ArrayList<>();
        this.lifeLessons = new ArrayList<>();  // Initialize the lifeLessons list
    }

    public Game(List<Integer> secretCombination) {
        this.secretCombination = secretCombination;
        this.remainingGuesses = 10;
        this.guessHistory = new ArrayList<>();
        this.lifeLessons = new ArrayList<>();  // Initialize the lifeLessons list
        this.isGameOver = false;
        this.gameState = "in-progress";
    }

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

    public List<String> getLifeLessons() {
        return lifeLessons;  // Getter for life lessons
    }

    public void setLifeLessons(List<String> lifeLessons) {
        this.lifeLessons = lifeLessons;  // Setter for life lessons
    }

    public void decreaseRemainingGuesses() {
        if (remainingGuesses > 0) {
            remainingGuesses--;
        }
        if (remainingGuesses == 0) {
            gameState = "lost";
        }
    }

    public boolean isGameOver() {
        return remainingGuesses == 0 || "won".equals(gameState);
    }

    public void setGameOver(boolean gameOver) {
        this.isGameOver = gameOver;
        if (gameOver) {
            this.gameState = "lost";
        }
    }

    public void addGuessHistory(List<Integer> guess, String feedback, String lifeLesson) {
        GuessHistory entry = new GuessHistory(guess, feedback, lifeLesson);
        this.guessHistory.add(entry.toString());
        this.lifeLessons.add(lifeLesson);  // Add the life lesson to the history
    }

    @Override
    public String toString() {
        return "Game{id=" + id +
                ", secretCombination=" + secretCombination +
                ", remainingGuesses=" + remainingGuesses +
                ", gameState='" + gameState + "'}";
    }

    public static class GuessHistory {
        private List<Integer> guess;
        private String feedback;
        private String lifeLesson;

        public GuessHistory(List<Integer> guess, String feedback, String lifeLesson) {
            this.guess = guess;
            this.feedback = feedback;
            this.lifeLesson = lifeLesson;
        }

        @Override
        public String toString() {
            return "Guess{" +
                    "guess=" + guess +
                    ", feedback='" + feedback + '\'' +
                    ", lifeLesson='" + lifeLesson + '\'' +
                    '}';
        }
    }
}
