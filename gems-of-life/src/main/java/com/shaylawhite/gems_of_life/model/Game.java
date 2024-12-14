package com.shaylawhite.gems_of_life.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
public class Game {
    @Id
    private Long id;

    // Corrected: Only one guessesHistory declaration
    @ElementCollection
    @CollectionTable(name = "guesses_history", joinColumns = @JoinColumn(name = "game_id"))
    @Column(name = "guess")
    private List<List<Integer>> guessesHistory; // List of past guesses

    private int attemptsLeft; // Number of remaining attempts
    private String currentLifeLesson; // The life lesson associated with the current level
    private boolean isWon; // Flag indicating whether the game is won
    private int currentLevel; // The current level the player is at

    // New: Declare randomCombination as List<Integer>
    private List<Integer> randomCombination;

    private List<String> lifeLessons = new ArrayList<>(Arrays.asList(
            "💎 Grit: Keep pushing through challenges, and you will reach your goal.",
            "✨ Self-Learning: Embrace new knowledge and skills to advance in life.",
            "🔍 Problem-Solving: Every challenge is an opportunity to find a creative solution.",
            "💪 Perseverance: Consistency is key, even when things seem tough.",
            "🔥 Passion: Follow your heart, and your passion will drive your success.",
            "💖 Self-Worth: Recognize your value, and don’t let others define it.",
            "💫 Belief in Yourself: You are capable of achieving great things.",
            "🌟 Uniqueness: Embrace what makes you different, it’s your superpower."
    ));

    // Constructor
    public Game() {
        // Initialize randomCombination as a List of Integers
        this.randomCombination = new ArrayList<>(Arrays.asList(0, 0, 0, 0)); // Example initialization

        // Initialize guessesHistory as an empty List
        this.guessesHistory = new ArrayList<>();

        this.attemptsLeft = 10; // Default number of attempts
        this.isWon = false;
        this.currentLevel = 1; // Start at level 1
    }

    // Getters and Setters
    public List<Integer> getRandomCombination() {
        return randomCombination;
    }

    public void setRandomCombination(List<Integer> randomCombination) {
        this.randomCombination = randomCombination;
    }

    public List<List<Integer>> getGuessesHistory() {
        return guessesHistory;
    }

    public void setGuessesHistory(List<List<Integer>> guessesHistory) {
        this.guessesHistory = guessesHistory;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public void setAttemptsLeft(int attemptsLeft) {
        this.attemptsLeft = attemptsLeft;
    }

    public String getCurrentLifeLesson() {
        return currentLifeLesson;
    }

    public void setCurrentLifeLesson(String currentLifeLesson) {
        this.currentLifeLesson = currentLifeLesson;
    }

    public boolean isWon() {
        return isWon;
    }

    public void setWon(boolean isWon) {
        this.isWon = isWon;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }
}
