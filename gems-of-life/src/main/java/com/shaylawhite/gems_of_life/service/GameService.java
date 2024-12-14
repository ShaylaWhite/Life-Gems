package com.shaylawhite.gems_of_life.service;

import com.shaylawhite.gems_of_life.exception.GameNotFoundException;
import com.shaylawhite.gems_of_life.model.Game;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GameService {

    private Game currentGame;

    private List<String> lifeLessons = new ArrayList<>(Arrays.asList(
            "Grit: Keep pushing through challenges, and you will reach your goal. 💎",
            "Self-Learning: Embrace new knowledge and skills to advance in life. ✨",
            "Problem-Solving: Every challenge is an opportunity to find a creative solution. 🔍",
            "Perseverance: Consistency is key, even when things seem tough. 💪",
            "Passion: Follow your heart, and your passion will drive your success. 🔥",
            "Self-Worth: Recognize your value, and don’t let others define it. 💖",
            "Belief in Yourself: You are capable of achieving great things. 💫",
            "Uniqueness: Embrace what makes you different, it’s your superpower. 🌟"
    ));

    // Start a new game and initialize game state
    public Game startNewGame() {
        currentGame = new Game();
        List<Integer> randomCombination = generateRandomCombination();
        currentGame.setRandomCombination(randomCombination);
        currentGame.setAttemptsLeft(10); // Reset attempts to 10
        currentGame.setWon(false); // Set game as not won initially
        currentGame.setGuessesHistory(new ArrayList<>()); // Initialize guess history
        currentGame.setCurrentLevel(1); // Start at level 1
        currentGame.setCurrentLifeLesson(lifeLessons.get(0)); // First lesson: "Grit"
        return currentGame;
    }

    // Generate a random combination of 4 numbers between 0 and 7 (inclusive)
    private List<Integer> generateRandomCombination() {
        List<Integer> combination = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            combination.add((int) (Math.random() * 8)); // Random number between 0 and 7
        }
        return combination;
    }

    // Check the player's guess and return feedback
    public String checkGuess(List<Integer> playerGuess) {
        if (currentGame == null) {
            throw new GameNotFoundException("No active game found. Please start a new game.");
        }

        if (currentGame.getAttemptsLeft() <= 0) {
            return "Game over! You've used all attempts.";
        }

        int correctPositions = 0;
        int correctNumbers = 0;

        List<Integer> randomCombination = currentGame.getRandomCombination();
        for (int i = 0; i < 4; i++) {
            if (playerGuess.get(i).equals(randomCombination.get(i))) {
                correctPositions++;
            } else if (randomCombination.contains(playerGuess.get(i))) {
                correctNumbers++;
            }
        }

        // Save the guess to history
        currentGame.getGuessesHistory().add(new ArrayList<>(playerGuess));

        currentGame.setAttemptsLeft(currentGame.getAttemptsLeft() - 1);

        if (correctPositions == 4) {
            currentGame.setWon(true);
            String feedback = "Congratulations! You've cracked the code.";
            currentGame.setCurrentLevel(currentGame.getCurrentLevel() + 1);

            if (currentGame.getCurrentLevel() - 1 < lifeLessons.size()) {
                currentGame.setCurrentLifeLesson(lifeLessons.get(currentGame.getCurrentLevel() - 1));
                feedback += " 🌟 You've advanced to the next level: " + currentGame.getCurrentLifeLesson();
            }
            return feedback;
        }

        return "You got " + correctPositions + " correct positions and " + correctNumbers + " correct numbers.";
    }

    // Get the current game instance
    public Game getCurrentGame() {
        return currentGame;
    }

    // Check if there is an active game
    public boolean isGameActive() {
        return currentGame != null;
    }
}



