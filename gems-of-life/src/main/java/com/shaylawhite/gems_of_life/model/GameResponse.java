package com.shaylawhite.gems_of_life.model;

public class GameResponse {
    private String message;
    private int attemptsLeft;
    private int currentLevel;
    private boolean isWon;
    private String currentLifeLesson; // New field for the life lesson

    public GameResponse(String message, int attemptsLeft, int currentLevel, boolean isWon, String currentLifeLesson) {
        this.message = message;
        this.attemptsLeft = attemptsLeft;
        this.currentLevel = currentLevel;
        this.isWon = isWon;
        this.currentLifeLesson = currentLifeLesson;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public void setAttemptsLeft(int attemptsLeft) {
        this.attemptsLeft = attemptsLeft;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public boolean isWon() {
        return isWon;
    }

    public void setWon(boolean isWon) {
        this.isWon = isWon;
    }

    public String getCurrentLifeLesson() {
        return currentLifeLesson;
    }

    public void setCurrentLifeLesson(String currentLifeLesson) {
        this.currentLifeLesson = currentLifeLesson;
    }
}


